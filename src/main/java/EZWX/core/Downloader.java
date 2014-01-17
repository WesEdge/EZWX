package EZWX.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.io.IOException;

public class Downloader {

    public Downloader(){

    }

    public byte[] httpGet(URL url) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = url.openStream ();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                outStream.write(byteChunk, 0, n);
            }
        }
        finally {
            if (is != null) { is.close(); }
            return outStream.toByteArray();
        }

    }

    public static byte[] get(URL url) throws IOException{
        return new Downloader().httpGet(url);
    }

}
