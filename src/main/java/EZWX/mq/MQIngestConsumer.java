package EZWX.mq;

import EZWX.core.Serializer;
import EZWX.interfaces.MQActor;

public class MQIngestConsumer extends MQConsumer {

    public MQIngestConsumer(MQActor actor) throws Exception {
        super(actor);
    }

    protected void consume(MQActor actor, MQArgs args) throws Exception{
        actor.execute(args);
    }

}
