package cn.deskie.sysserver.task;

import cn.deskie.sysinterface.service.business.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchCrawlerTask {

    private static final Logger logger = LoggerFactory.getLogger(BatchCrawlerTask.class);

    @Autowired
    private BatchService batchService;

    /**
     * 批次爬虫定时任务
     */
//    @Scheduled(cron = "* * 0/6 * * *")
    public void start(){
        logger.info("开始执行批次爬虫定时任务。。。");
        batchService.startCrawlerTask();
        logger.info("执行批次爬虫定时任务结束。。。");
    }
}