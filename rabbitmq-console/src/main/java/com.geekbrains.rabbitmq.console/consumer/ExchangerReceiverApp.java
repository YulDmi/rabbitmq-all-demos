package com.geekbrains.rabbitmq.console.consumer;

import com.rabbitmq.client.*;

public class ExchangerReceiverApp {
    private static final String EXCHANGE_NAME = "example-2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "java");
        System.out.println("Waiting massage");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String massage = new String(delivery.getBody(), "UTF-8");
            System.out.println("received " + massage);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag ->{});

    }
}
