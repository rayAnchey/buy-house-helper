package cn.deskie.sysserver.rocketmq;

import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysentity.entity.HouseDetail;
import cn.deskie.sysentity.entity.Project;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class RocketMQServer {
    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    private final DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

    private static final Logger logger = LoggerFactory.getLogger(RocketMQServer.class);

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @PostConstruct
    public void defaultMQProducer() {
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        try {
            logger.info("MQ:生产者启动中...");
            producer.setProducerGroup(producerGroup);
            producer.start();
            logger.info("MQ:生产者启动成功...");
        } catch (MQClientException e) {
            logger.info("MQ:生产者启动失败："+e.getErrorMessage());
        }
    }
    public void sendMessage(Object data) {
        try {
            String tags = "";
            String key = "";
            if(data instanceof Batch){
                if("0".equals(((Batch) data).getIsDownloaded())){
                    tags = "attch";
                }else {
                    tags = "project";
                }
                key = ((Batch) data).getId();

            }else if(data instanceof Project){
                tags = "houseInfo";
                key = ((Project) data).getId();
            }else {
                throw new IllegalArgumentException("MQ发送消息传入消息体类型错误:"+data.getClass());
            }
            byte[] messageBody = JSON.toJSONString(data).getBytes(RemotingHelper.DEFAULT_CHARSET);

            Message mqMsg = new Message("violet", tags, key, messageBody);

            producer.send(mqMsg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("MQ: 生产者发送消息成功： {}", sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    logger.error("MQ: 生产者发送消息失败："+throwable.getMessage(), throwable);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @PreDestroy
    public void stop() {
        if (producer != null) {
            producer.shutdown();
            logger.info("MQ：关闭生产者");
        }
    }
}