package com.kris.study._02workqueue.work_fair;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connrction = RabbitmqUtil.getConnrction();
        Channel channel = connrction.createChannel();
        /*
            每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次处理一个消息。
            限制发送给同一个消费者不能超过一条
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 20; i++) {
            String msg = "hello"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(50);
        }
        channel.close();
        connrction.close();
    }

}
