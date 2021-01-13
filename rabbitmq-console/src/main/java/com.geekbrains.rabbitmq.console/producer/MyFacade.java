package com.geekbrains.rabbitmq.console.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyFacade {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String getString() throws IOException {
        return reader.readLine();
    }

}
