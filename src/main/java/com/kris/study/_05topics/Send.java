package com.kris.study._05topics;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtil.getConnrction();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg = "商品。。";

        channel.basicPublish(EXCHANGE_NAME,"goods.add",null,msg.getBytes());
        System.out.println("--send:"+msg);
        channel.close();
        connection.close();
    }
}
