package com.kris.study._02workqueue.work_fair;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connrction = RabbitmqUtil.getConnrction();
        final Channel channel = connrction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[2]收到消息"+new String(body,"utf-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] down");
                    channel.basicAck(envelope.getDeliveryTag(),false);

                }
            }
        };
        //自动应答关闭
        boolean aotuAck = false;
        channel.basicConsume(QUEUE_NAME,aotuAck,consumer);
    }
}
