package com.geekbrains.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangerSenderApp  {
    private final static String EXCHANGE_NAME = "example-2";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String massage = "info: Hello world";
            channel.basicPublish(EXCHANGE_NAME, "java", null, massage.getBytes("UTF-8"));
            System.out.println("Sent : " + massage);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }
}
