package com.kris.study._04routing;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    public static final String QUEUE_NAME = "test_queue_direct_1";
    public static final String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connrction = RabbitmqUtil.getConnrction();
        Channel channel = connrction.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //设置一次发送一个
        channel.basicQos(1);
        //绑定routingKey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[1]收到消息"+new String(body,"utf-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] down");

                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //自动应答关闭
        boolean aotuAck = false;
        channel.basicConsume(QUEUE_NAME,aotuAck,consumer);
    }
}
