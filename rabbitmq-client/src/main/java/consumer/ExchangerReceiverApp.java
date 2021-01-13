package consumer;

import com.rabbitmq.client.*;

public class ExchangerReceiverApp {
    private static final String EXCHANGE_NAME = "example-2";
 //   private static final String EXCHANGE_NAME2 = "example-2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
      //  Channel channel2 = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      // channel2.exchangeDeclare(EXCHANGE_NAME2, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
      String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "java");

       channel.queueBind(queueName2, EXCHANGE_NAME, "php");
        System.out.println("Waiting massage");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String massage = new String(delivery.getBody(), "UTF-8");

            System.out.println("received " + delivery.getEnvelope().getRoutingKey() + " - " + massage);
        };

        channel.basicConsume(queueName, false, deliverCallback, consumerTag ->{});
        channel.basicConsume(queueName2, false, deliverCallback, consumerTag ->{});

    }
}
