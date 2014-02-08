package EZWX.core;

public class ConsoleWriter {

    public static void writeKVP(String key, float value){
        writeKVP(key, String.valueOf(value));
    }

    public static void writeKVP(String key, int value){
        writeKVP(key, String.valueOf(value));
    }

    public static void writeKVP(String key, String value){
        write(key + ": " + value);
    }

    public static void write(String text){
        System.out.println(text);
    }

}
