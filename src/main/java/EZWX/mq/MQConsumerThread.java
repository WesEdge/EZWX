package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQConsumerThread implements Runnable {

    private MQActor _actor;
    private MQConsumer _consumer;
    private String _message;

    MQConsumerThread(MQConsumer consumer, MQActor actor, String message) {
        _consumer = consumer;
        _actor = actor;
        _message = message;
    }

    public void run() {

        try {
            _consumer.consume(_actor, _message);
        } catch (Exception ex) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, ex);
        }

    }

    public static void start(MQConsumer consumer, MQActor actor, String message) throws Exception{
        Thread t = new Thread(new MQConsumerThread(consumer, actor, message));
        t.start();
    }

}