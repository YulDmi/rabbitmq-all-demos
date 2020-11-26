package com.geekbrains.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ExchangerSenderApp {
    private final static String EXCHANGE_NAME = "example-2";
    private final static List<String> list = new ArrayList<>();

    static {
        list.add("php");
        list.add("java");
        list.add("js");
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Введи название языка статьи из списка :");
            list.forEach(System.out::println);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String lang = br.readLine().trim().toLowerCase();
            System.out.println("Введите полное имя файла :");
            String file = br.readLine();
            byte[] article = Files.readAllBytes(Paths.get(file));
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
                sendMessage(lang, article, channel);
            } catch (TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendMessage(String lang, byte[] article, Channel channel) throws IOException {

        //  byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\ASUS\\IdeaProjects\\Cloud\\rabbitmq-all-demos\\rabbitmq-console\\src\\main\\resources\\text.txt"));
        channel.basicPublish(EXCHANGE_NAME, lang, null, article);
        //  channel.basicPublish(EXCHANGE_NAME, "java", null, array);
        System.out.println("Sent massage ");
    }
}
