package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQIngestConsumer extends MQConsumer {

    public MQIngestConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, byte[] bytes) throws Exception{
        MQArgs args = new MQArgs(bytes);
        actor.execute(args);
    }

}
