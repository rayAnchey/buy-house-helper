package cn.deskie.sysinterface.service.business;

import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysentity.entity.Project;

import java.util.List;

public interface ProjectService {
    int save(Project project);
    int update(Project project);
    int saveExcelToDB(Batch batch);
    int batchSave(List<Project> list);
    Project getById(String id);
}