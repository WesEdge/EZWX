package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQPersistanceConsumer extends MQConsumer {

    public MQPersistanceConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, byte[] bytes) throws Exception{
        MQArgs args = new MQArgs(bytes);
        args.setBytes(bytes);
        actor.execute(args);
    }

}