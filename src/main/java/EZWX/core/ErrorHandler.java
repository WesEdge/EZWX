package EZWX.core;

public class ErrorHandler {

    public ErrorHandler(Exception ex){
        Logger.log(ex.toString());
    }

    public static void handle(Exception ex){
        new ErrorHandler(ex);
    }

}
