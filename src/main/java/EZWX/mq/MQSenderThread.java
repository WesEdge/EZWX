package EZWX.mq;

public class MQSenderThread implements Runnable {

    private MQSender _sender;

    MQSenderThread(MQSender sender) {
        _sender = sender;
    }

    public void run() {

        try {
            MQArgs message = _sender.getMessage();
            _sender.send(message);
        } catch (Exception ex) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, ex);
        }

    }

    public static void start(MQSender sender) throws Exception{
        Thread t = new Thread(new MQSenderThread(sender));
        t.start();
    }

}