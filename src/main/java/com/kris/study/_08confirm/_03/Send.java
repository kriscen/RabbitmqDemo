package com.kris.study._08confirm._03;

import com.kris.study.utils.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitmqUtil.getConnrction();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect，将channel设置成confirm模式
        channel.confirmSelect();
        String msg = "hello confirm 2";

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());

        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("--handleNack--multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("--handleNack--multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //有问题
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("--handleNack--multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println( "--handleNack--multiple false ");
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }
    }
}
