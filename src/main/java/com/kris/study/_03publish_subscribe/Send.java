package com.kris.study._03publish_subscribe;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.DataInput;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connrction = RabbitmqUtil.getConnrction();
        Channel channel = connrction.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout"); //分发

        String msg = "hello ps";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("send : "+msg);
        channel.close();
        connrction.close();
    }
}
