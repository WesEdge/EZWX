package EZWX.mq;

import EZWX.core.Logger;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;

public abstract class MQSender {

    public MQSender() throws Exception {

    }

    public void send() throws Exception{
        MQSenderThread.start(this);
    }

    public void send(MQArgs args) throws IOException{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(args.getQueueHost());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(args.getQueueName(), false, false, false, null);
        channel.basicPublish("", args.getQueueName(), null, args.getBytes());

        channel.close();
        connection.close();

    }

    abstract protected MQArgs getMessage() throws Exception;

    private void log(String msg){
        Logger.log(msg);
    }

}

