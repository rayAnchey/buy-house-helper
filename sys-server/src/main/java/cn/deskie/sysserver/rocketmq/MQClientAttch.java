package cn.deskie.sysserver.rocketmq;


import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysinterface.service.business.BatchService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


@Component
public class MQClientAttch implements MessageListenerConcurrently{
    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.attchConsumer}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);

    private static final Logger logger = LoggerFactory.getLogger(MQClientAttch.class);

    @Autowired
    private BatchService batchService;

    @PostConstruct
    public void defaultMQPushConsumer() {


        try {
            logger.info("MQ：启动附件处理消费者...");
            //指定NameServer地址，多个地址以 ; 隔开
            consumer.setNamesrvAddr(namesrvAddr);
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("violet", "attch");
            consumer.setConsumerGroup(consumerGroup);
            // 集群消费模式
            consumer.setMessageModel(MessageModel.CLUSTERING);
            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(this);
            consumer.start();
        } catch (MQClientException e) {
            logger.error("MQ：启动附件处理消费者失败：{}-{}", e.getResponseCode(), e.getErrorMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        int index = 0;
        try {
            for (; index < msgs.size(); index++) {
                MessageExt msg = msgs.get(index);

                String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                //处理收到的消息
                Batch batch = (Batch) JSON.parseObject(messageBody,Batch.class);
                batchService.downLoadAttachments(batch);
                logger.info("MQ：附件处理消费者接收新信息:{} {}", msg.getTags(), msg.getKeys());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (index < msgs.size()) {
                context.setAckIndex(index + 1);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @PreDestroy
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            logger.error("MQ：关闭附件处理消费者");
        }
    }
}