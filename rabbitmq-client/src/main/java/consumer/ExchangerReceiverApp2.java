package consumer;

        import com.rabbitmq.client.*;

public class ExchangerReceiverApp2 {
    private static final String EXCHANGE_NAME = "example-2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "java");
        System.out.println("Waiting massage");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String massage = new String(delivery.getBody(), "UTF-8");
            System.out.println("received " + massage);
        };

        channel.basicConsume(queueName, false, deliverCallback, consumerTag ->{});

    }
}
