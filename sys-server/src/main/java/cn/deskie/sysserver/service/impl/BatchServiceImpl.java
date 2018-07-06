package cn.deskie.sysserver.service.impl;

import cn.deskie.syscommon.crawler.BatchCrawler;
import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysserver.mapper.BatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImpl.class);

    @Autowired
    private BatchMapper batchMapper;

    @Override
    public void startCrawlerTask() {
        logger.info("");
        BatchCrawler.fileList = new ArrayList<Batch>();
        BatchCrawler crawler2 = new BatchCrawler("d:/opt/crawler", true);
        crawler2.addSeed("http://wjj.xa.gov.cn/ptl/def/def/index_1285_3887_ci_trid_4416419.html");
        crawler2.setThreads(1);
        crawler2.setResumable(false);
        try {
            crawler2.start(30);
        } catch (Exception e) {

        }
        BatchSave(BatchCrawler.fileList);
        BatchCrawler.fileList = null;
    }

    @Override
    public int save(Batch batch) {
        batch.setAddTime(new Date());
        return batchMapper.insert(batch);
    }

    @Override
    public int BatchSave(List<Batch> list) {
        for(Batch batch:list){
            batch.setAddTime(new Date());
        }
        return batchMapper.batchSave(list);
    }

}