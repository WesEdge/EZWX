package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQConsumerThread implements Runnable {

    private MQActor _actor;
    private MQConsumer _consumer;
    private byte[] _bytes;

    MQConsumerThread(MQConsumer consumer, MQActor actor, byte[] bytes) {
        _consumer = consumer;
        _actor = actor;
        _bytes = bytes;
    }

    public void run() {

        try {
            _consumer.consume(_actor, _bytes);
        } catch (Exception ex) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, ex);
        }

    }

    public static void start(MQConsumer consumer, MQActor actor, byte[] bytes) throws Exception{
        Thread t = new Thread(new MQConsumerThread(consumer, actor, bytes));
        t.start();
    }

}