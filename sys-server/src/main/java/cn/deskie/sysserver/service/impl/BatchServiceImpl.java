package cn.deskie.sysserver.service.impl;

import cn.deskie.syscommon.crawler.BatchCrawler;
import cn.deskie.syscommon.utils.HttpDownLoadUtils;
import cn.deskie.syscommon.utils.IdGen;
import cn.deskie.syscommon.utils.ZipResolver;
import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysentity.entity.Project;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysinterface.service.business.ProjectService;
import cn.deskie.sysserver.mapper.BatchMapper;
import cn.deskie.sysserver.rocketmq.RocketMQServer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BatchServiceImpl implements BatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImpl.class);

    private String sotrePath = "D:\\opt\\filedown\\";//// TODO: 2018/7/9 参数化

    @Autowired
    private BatchMapper batchMapper;
    @Autowired
    private RocketMQServer rocketMQServer;

    @Override
    public void startCrawlerTask() {
        logger.info("批次爬虫启动。。。");
        if (BatchCrawler.fileList != null) {
            logger.info("批次爬虫正在执行中，请稍后查看。。。");
            return;
        }
        BatchCrawler.fileList = new ArrayList<Batch>();
        BatchCrawler crawler2 = new BatchCrawler("d:/opt/crawler", true);
        crawler2.addSeed("http://wjj.xa.gov.cn/ptl/def/def/index_1285_3887_ci_trid_4416419.html");
        crawler2.setThreads(1);
        crawler2.setResumable(false);
        try {
            crawler2.start(30);
            BatchSave(BatchCrawler.fileList);
        } catch (Exception e) {
            logger.error("批次爬虫执行异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            BatchCrawler.fileList = null;
        }
    }

    @Override
    public int save(Batch batch) {
        batch.setAddTime(new Date());
        return batchMapper.insert(batch);
    }

    @Override
    public int BatchSave(List<Map> list) {
        List<Batch> batchList = new ArrayList<>();
        Integer maxBatchNo = getMaxBatchNo();
        for (Map map : list) {
            Batch batch = new Batch();
            batch.setBatchName(MapUtils.getString(map, "batchName"));
            Integer batNo = getBatchNoFromTitle(batch.getBatchName());
            if(batNo<=maxBatchNo){
                continue;
            }
            batch.setBatchNo(batNo);
            batch.setId(IdGen.uuid());
            batch.setReferenceno(MapUtils.getString(map,"reference"));
            batch.setPageUrl(MapUtils.getString(map, "pageUrl"));
            batch.setAttachmentUrl(MapUtils.getString(map, "attachmentUrl"));
            batch.setPublicTime((Date) map.get("publicTime"));
            batch.setIsDownloaded("0");
            batch.setIsResolved("0");
            batch.setAddTime(new Date());
            batchList.add(batch);
        }
        int num = batchList.size()>0?batchMapper.batchSave(batchList):0;
        //发送mq批次信息，继续处理附件
        for(Batch batch:batchList){
            rocketMQServer.sendMessage(batch);
        }
        return num;
    }

    @Override
    public void downLoadAttachments(Batch batch) {
            String fileName = StringUtils.substringAfterLast(batch.getAttachmentUrl(),"/");
            boolean success = HttpDownLoadUtils.downLoadFromUrl(batch.getAttachmentUrl(),fileName,sotrePath);
            if(success){
                //更新已下载和文件路径
                batch.clearProperties();
                batch.setIsDownloaded("1");
                batch.setAttachmentName(sotrePath+fileName);
                batch.setUpdateTime(new Date());
                batchMapper.updateByPrimaryKeySelective(batch);
                if(batch.getBatchNo()<2018023){
                    //20批之前的excel格式不一样暂不入库
                    return;
                }
                String attachName = batch.getAttachmentName();
                ZipResolver.unzip(attachName,attachName.replace(".zip",""));
                batch = batchMapper.selectByPrimaryKey(batch.getId());
                rocketMQServer.sendMessage(batch);
            }
    }



    @Override
    public List<Batch> findByProperty(String propertyName,Object value) {
        return batchMapper.findByProperty(propertyName,value);
    }

    @Override
    public Batch findById(String id) {
        return batchMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Batch batch) {
        return batchMapper.updateByPrimaryKeySelective(batch);
    }

    private Integer getBatchNoFromTitle(String title) {
        String year = StringUtils.substringBetween(title,"（","年第");
        String no = StringUtils.substringBetween(title,"年第","批）");
        return Integer.parseInt(year+StringUtils.leftPad(no,3,"0"));
    }
    private Integer getMaxBatchNo(){
        return batchMapper.getMaxBatchNo();
    }





}