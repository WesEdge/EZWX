package EZWX.mq;

import EZWX.interfaces.MQActor;

public class MQIngestConsumer extends MQConsumer {

    public MQIngestConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, String message) throws Exception{
        MQArgs args = new MQArgs(message);
        args.setUrl(message);
        actor.execute(args);
    }

}
