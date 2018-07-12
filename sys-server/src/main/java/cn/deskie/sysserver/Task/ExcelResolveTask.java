package cn.deskie.sysserver.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExcelResolveTask {

    /**
     * 数据解析定时任务
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void start(){

    }
}