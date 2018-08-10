package cn.deskie.sysserver.task;

import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysinterface.service.business.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResolveExcelTask {

    @Autowired
    private BatchService batchService;
    @Autowired
    private ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(ResolveExcelTask.class);

    /**
     * 定时任务检查未处理的批次信息并处理入库
     */
    @Scheduled(cron = "* 0/10 * * * *")
    public void start(){
        List<Batch> undownloadBatch = batchService.findByProperty("is_resolved","0");
        if(null!=undownloadBatch&&undownloadBatch.size()>0){
            logger.info("附件处理任务开始，未处理标段{}个。",undownloadBatch.size());
            for(Batch batch:undownloadBatch){
                projectService.saveExcelToDB(batch);
            }
        }
    }
}