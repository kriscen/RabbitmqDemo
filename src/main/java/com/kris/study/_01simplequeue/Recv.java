package com.kris.study._01simplequeue;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接
        Connection connection = RabbitmqUtil.getConnrction();
        //获取通道
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息进入队列触发方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv:" + new String(body, "utf-8"));
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,consumer);
    }
}
