package EZWX.core;

public class Logger {

    public Logger(String msg){
        System.out.println(msg);
    }

    public static void log(String msg){
        new Logger(msg);
    }

}

