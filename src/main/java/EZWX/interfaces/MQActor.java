package EZWX.interfaces;

import EZWX.mq.*;

public interface MQActor {
    public void execute(MQArgs args) throws Exception;
}