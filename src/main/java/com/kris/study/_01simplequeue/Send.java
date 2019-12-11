package com.kris.study._01simplequeue;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接
        Connection connrction = RabbitmqUtil.getConnrction();
        //创建通道
        Channel channel = connrction.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello jb"+new Date().toString();
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("-- send mag --"+msg);
        channel.close();
        connrction.close();
    }
}
