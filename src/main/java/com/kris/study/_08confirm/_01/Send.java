package com.kris.study._08confirm._01;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqUtil.getConnrction();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect，将channel设置成confirm模式
        channel.confirmSelect();
        String msg = "hello confirm";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if(!channel.waitForConfirms()){
            System.out.println("failed");
        }else{
            System.out.println("ok");
        }

        channel.close();
        connection.close();

    }
}
