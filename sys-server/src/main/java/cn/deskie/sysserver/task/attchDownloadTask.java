package cn.deskie.sysserver.task;

import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysinterface.service.business.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttchDownloadTask {

    @Autowired
    private BatchService batchService;
    private static final Logger logger = LoggerFactory.getLogger(AttchDownloadTask.class);

    /**
     * 定时任务检查未下载的批次信息并下载处理
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void start(){
        List<Batch> undownloadBatch = batchService.findByProperty("is_downloaded","0");
        if(null!=undownloadBatch&&undownloadBatch.size()>0){
            logger.info("附件下载任务开始，未下载标段{}个。",undownloadBatch.size());
            for(Batch batch:undownloadBatch){
                batchService.downLoadAttachments(batch);
            }
        }
    }
}