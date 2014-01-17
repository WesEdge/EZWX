package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQPersistanceConsumer extends MQConsumer {

    public MQPersistanceConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, String message) throws Exception{
        MQArgs args = new MQArgs(message);
        args.setUrl(message);
        actor.execute(args);
    }

}