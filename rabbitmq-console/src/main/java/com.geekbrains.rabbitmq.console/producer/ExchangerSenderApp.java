package com.geekbrains.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class ExchangerSenderApp  {
  private final static String EXCHANGE_NAME = "example-2";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\ASUS\\IdeaProjects\\Cloud\\rabbitmq-all-demos\\rabbitmq-console\\src\\main\\resources\\text.txt"));
            channel.basicPublish(EXCHANGE_NAME, "php", null, array);
            channel.basicPublish(EXCHANGE_NAME, "java", null, array);
            System.out.println("Sent massage ");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }
}
