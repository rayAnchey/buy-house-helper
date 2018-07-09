package cn.deskie.sysinterface.service.business;

import cn.deskie.sysentity.entity.Batch;

import java.util.List;
import java.util.Map;

public interface BatchService {
    void startCrawlerTask();
    int save(Batch batch);
    int BatchSave(List<Map> list);
    void downLoadAttachments();
    void unZipAndSaveExcelToDB();
    List<Batch> findByProperty(String propertyName,Object value);
    Batch findById(String id);
}