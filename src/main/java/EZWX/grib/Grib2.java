package EZWX.grib;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.List;
import EZWX.core.StringWriter;
import ucar.grib.grib2.*;
import ucar.unidata.io.RandomAccessFile;
import ucar.grib.grib2.Grib2GridDefinitionSection;
import EZWX.core.ConsoleWriter;

public class Grib2 {

    private URL _url;
    private RandomAccessFile _randomAccessFile;
    private Grib2Input _gribInput;
    private Map<String, Grib2GridDefinitionSection> _gdsMap;
    private List<Grib2Product> _products;
    private boolean _isLoaded = false;
    byte[] _bytes;

    public static Grib2 load(URL url)  throws IOException{
        return new Grib2(url);
    }

    private Grib2(URL url) throws IOException{

        _url = url;

        String tempFileName = url.getHost() + "_" + url.getPath().replaceAll("/","_") + "_" + String.valueOf(System.currentTimeMillis());
        //String tempFileName = String.valueOf(System.currentTimeMillis());
        File tempFile = File.createTempFile(tempFileName, null, null);

        try{

            _bytes = EZWX.core.Downloader.get(url);

            _randomAccessFile = new RandomAccessFile(tempFileName, "rw");
            _randomAccessFile.write(_bytes);
            _randomAccessFile.close();
            _randomAccessFile = new RandomAccessFile(tempFileName, "rw");
            _randomAccessFile.order(RandomAccessFile.BIG_ENDIAN);
            _gribInput = new Grib2Input(_randomAccessFile);

            if (_gribInput.scan(true, false)) {
                ConsoleWriter.write("Grib2 Scan Success");
                _gdsMap = _gribInput.getGDSs();
                _products = _gribInput.getProducts();
                this._isLoaded = true;
            }
            else{
                ConsoleWriter.write("Grib2 Scan Failed");
            }

        }
        finally{
            File file = new File(tempFileName);
            file.delete();
        }


    }

    public String getGDSSummary() throws IOException{

        if ((_gdsMap == null) || (_gdsMap.size() < 1)){
            return "No GDS Found";
        }

        EZWX.core.StringWriter stringWriter = new StringWriter(true);

        for (Map.Entry<String, Grib2GridDefinitionSection> gds : _gdsMap.entrySet())
        {
            stringWriter.write("GDS Found");
            stringWriter.write("\n------ BEGIN GDS ------");

            Grib2GDSVariables gdsVars = gds.getValue().getGdsVars();
            //Grib2GridDefinitionSection gdsValues = gds.getValue(); // mostly deprecated, use getGdsVars instead

            stringWriter.writeKVP("key", gdsVars.getGdsKey());  // 801839731
            stringWriter.writeKVP("section", gdsVars.getSection());  // 3 = Grid Definition Section
            stringWriter.writeKVP("source", gdsVars.getSource());  // 0 = Latitude/Longitude (See Template 3.0)    Also called Equidistant Cylindrical or Plate Caree
            stringWriter.writeKVP("shape", gdsVars.getShape());  // 1 = Earth assumed spherical with radius specified (in m) by data producer
            stringWriter.writeKVP("scan mode", gdsVars.getScanMode());  // 80 = ?
            stringWriter.writeKVP("point count", gdsVars.getNumberPoints());  // 739297
            stringWriter.writeKVP("grid units", gdsVars.getGridUnits()); // m
            stringWriter.writeKVP("projection flag", gdsVars.getProjectionFlag()); //    0
            stringWriter.writeKVP("resolution", gdsVars.getResolution()); // 0
            stringWriter.writeKVP("sp lat", gdsVars.getSpLat()); //  -90
            stringWriter.writeKVP("sp lon", gdsVars.getSpLon()); //  0
            stringWriter.writeKVP("N", gdsVars.getN()); //   -9999
            stringWriter.writeKVP("scale value major", gdsVars.getScaleValueMajor()); // 0
            stringWriter.writeKVP("scale value minor", gdsVars.getScaleValueMinor()); // 0
            stringWriter.writeKVP("scale value radius", gdsVars.getScaleValueRadius()); //   6371200
            stringWriter.writeKVP("crc", gdsVars.calcCRC()); //  2.24254797E9
            stringWriter.writeKVP("Nx", gdsVars.calculateNx()); //   0
            stringWriter.writeKVP("Xo", gdsVars.getXo()); // -9999.0
            stringWriter.writeKVP("Yo", gdsVars.getYo()); // -9999.0
            stringWriter.writeKVP("Xp", gdsVars.getXp()); // -9999.0
            stringWriter.writeKVP("Yp", gdsVars.getYp()); // -9999.0
            stringWriter.writeKVP("unscaled La1", gdsVars.getUnscaledLa1()); //  20191999
            stringWriter.writeKVP("unscaled Lo1", gdsVars.getUnscaledLo1()); //  238445999

            stringWriter.write("------- END GDS -------");
        }

        return stringWriter.toString();

    }

    public String getProductSummary(){

        if ((_products == null) || (_products.size() < 1)){
            return "No Products Found";
        }

        EZWX.core.StringWriter stringWriter = new StringWriter(true);

        stringWriter.write(String.valueOf(_products.size()) + " Products Found...");

        for (Grib2Product product : _products)
        {

            stringWriter.write("\n------ BEGIN PRODUCT ------");

            stringWriter.writeKVP("section", product.getPDS().getPdsVars().getSection());   // 4 = product definition
            stringWriter.writeKVP("discipline", product.getDiscipline());   // 0 = temperature
            stringWriter.writeKVP("product definition template", product.getPDS().getPdsVars().getProductDefinitionTemplate()); // 0 = Analysis or forecast at a horizontal level or in a horizontal layer at a point in time.
            stringWriter.writeKVP("gds offset", product.getGdsOffset());    // 5067573.0
            stringWriter.writeKVP("pds offset", product.getPdsOffset());    // 5067654.0
            stringWriter.writeKVP("base time", product.getBaseTime());  // Fri Feb 07 17:00:00 CST 2014
            stringWriter.writeKVP("ref time", product.getRefTime());    // 1.39181398E12
            stringWriter.writeKVP("forecast date", product.getPDS().getPdsVars().getForecastDate());    // Mon Feb 10 03:00:00 CST 2014
            stringWriter.writeKVP("forecast time", product.getPDS().getPdsVars().getForecastTime());    // 58
            stringWriter.writeKVP("center id", product.getID().getCenter_id()); // 8
            stringWriter.writeKVP("product id length", product.getID().getLength());    // 21
            stringWriter.writeKVP("product length", product.getPDS().getPdsVars().getLength()); // 34

            /*
            stringWriter.writeKVP("local table version", product.getID().getLocal_table_version()); // 0 =
            stringWriter.writeKVP("master table version", product.getID().getMaster_table_version());   // 1 =
            stringWriter.writeKVP("product status", product.getID().getProductStatus());    // 0
            stringWriter.writeKVP("product status name", product.getID().getProductStatusName());   // Operational products
            stringWriter.writeKVP("product type", product.getID().getProductType());    // 1
            stringWriter.writeKVP("product type name", product.getID().getProductTypeName());   // Forecast products
            stringWriter.writeKVP("significance of RT", product.getID().getSignificanceOfRT()); // 1
            stringWriter.writeKVP("significance of RT name", product.getID().getSignificanceOfRTName());    // Start of forecast
            stringWriter.writeKVP("sub-center id", product.getID().getSubcenter_id());  // -9999
            stringWriter.writeKVP("percentile value", product.getPDS().getPdsVars().getPercentileValue());  // -1
            stringWriter.writeKVP("gen process id", product.getPDS().getPdsVars().getGenProcessId());   // 0
            stringWriter.writeKVP("gen process type", product.getPDS().getPdsVars().getGenProcessType());   // 2 = Forecast
            stringWriter.writeKVP("interval time end", product.getPDS().getPdsVars().getIntervalTimeEnd()); // -9999.0
            stringWriter.writeKVP("number coordinates", product.getPDS().getPdsVars().getNumberCoordinates());  // 0
            stringWriter.writeKVP("number ensemble forecasts", product.getPDS().getPdsVars().getNumberEnsembleForecasts()); // -9999
            stringWriter.writeKVP("parameter category", product.getPDS().getPdsVars().getParameterCategory());  // 0 = Temperature
            stringWriter.writeKVP("parameter number", product.getPDS().getPdsVars().getParameterNumber());  // 0 = Temperature
            stringWriter.writeKVP("perturbation number", product.getPDS().getPdsVars().getPerturbationNumber());    // -9999
            stringWriter.writeKVP("perturbation type", product.getPDS().getPdsVars().getPerturbationType());    // -9999
            stringWriter.writeKVP("probability type", product.getPDS().getPdsVars().getProbabilityType());  // -9999
            stringWriter.writeKVP("is ensemble", product.getPDS().getPdsVars().isEnsemble());   // false
            stringWriter.writeKVP("is ensemble derived", product.getPDS().getPdsVars().isEnsembleDerived());    // false
            stringWriter.writeKVP("is interval", product.getPDS().getPdsVars().isInterval());   // false
            stringWriter.writeKVP("is percentile", product.getPDS().getPdsVars().isPercentile());   // false
            stringWriter.writeKVP("is probability", product.getPDS().getPdsVars().isProbability()); // false
            stringWriter.writeKVP("statistical process type", product.getPDS().getPdsVars().getStatisticalProcessType());   // -1
            */

            stringWriter.write("------- END PRODUCT -------");
        }

        return stringWriter.toString();

    }

    public boolean isLoaded(){
        return this._isLoaded;
    }

}
