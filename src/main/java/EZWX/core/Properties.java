package EZWX.core;

import java.io.InputStream;
import java.io.IOException;

public class Properties extends java.util.Properties {

    private static Properties _props;

    public Properties() throws IOException{
        this("config.properties");
    }

    public Properties(String target) throws IOException{
        super();
        this.load(target);
    }

    public void load(String target) throws IOException {

        InputStream stream = Properties.class.getClassLoader().getResourceAsStream(target);
        //FileInputStream stream = new FileInputStream(target);
        this.load(stream);

    }

    public static Properties get() throws IOException{
        Properties props = new EZWX.core.Properties();
        return props;
    }

    public static String getValue(String key) throws IOException{
        if (Properties._props == null){
            Properties._props = Properties.get();
        }
        return _props.getProperty(key);
    }

}
