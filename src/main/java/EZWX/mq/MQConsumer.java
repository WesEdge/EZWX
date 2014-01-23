package EZWX.mq;

import EZWX.core.Logger;
import EZWX.core.Properties;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import EZWX.interfaces.MQActor;
import EZWX.mq.MQConsumerThread;

public abstract class MQConsumer {

    public MQConsumer(MQActor mqActor) throws Exception {

        String queueHost = Properties.getValue("queueHost");
        log("queue host: " + queueHost);
        String queueName = Properties.getValue("queueNameIn");
        log("queue name: " + queueName);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        log("Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] bytes = delivery.getBody();
            log("Received Queue Message");

            //MQConsumerThread.start(this, mqActor, bytes);

            Thread t = new Thread(new MQConsumerThread(this, mqActor, bytes));
            t.start();

        }

    }

    abstract protected void consume(MQActor mqActor, byte[] bytes) throws Exception;

    private void log(String msg){
        Logger.log("RabbitMQ [*] " + msg);
    }

}
