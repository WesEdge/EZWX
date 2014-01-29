package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQPersistanceConsumer extends MQConsumer {

    public MQPersistanceConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, MQArgs args) throws Exception{
        actor.execute(args);
    }

}