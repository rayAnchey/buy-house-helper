package cn.deskie.sysserver.Task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchCrawlerTask {

    /**
     * 批次爬虫定时任务
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void start(){

    }
}