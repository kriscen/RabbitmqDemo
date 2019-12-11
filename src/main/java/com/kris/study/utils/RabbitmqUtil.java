package com.kris.study.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqUtil {

    //获取链接
    public static Connection getConnrction() throws IOException, TimeoutException {
        //链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_kris");
        factory.setUsername("kris");
        factory.setPassword("");
        return factory.newConnection();
    }


}
