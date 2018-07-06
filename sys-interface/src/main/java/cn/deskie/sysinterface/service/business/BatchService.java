package cn.deskie.sysinterface.service.business;

import cn.deskie.sysentity.entity.Batch;

import java.util.List;

public interface BatchService {
    void startCrawlerTask();
    int save(Batch batch);
    int BatchSave(List<Batch> list);
}