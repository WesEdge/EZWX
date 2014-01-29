package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQConsumerThread implements Runnable {

    private MQActor _actor;
    private MQConsumer _consumer;
    private MQArgs _args;

    MQConsumerThread(MQConsumer consumer, MQActor actor, MQArgs args) {
        _consumer = consumer;
        _actor = actor;
        _args = args;
    }

    public void run() {

        try {
            _consumer.consume(_actor, _args);
        } catch (Exception ex) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, ex);
        }

    }

    public static void start(MQConsumer consumer, MQActor actor, MQArgs args) throws Exception{
        Thread t = new Thread(new MQConsumerThread(consumer, actor, args));
        t.start();
    }

}