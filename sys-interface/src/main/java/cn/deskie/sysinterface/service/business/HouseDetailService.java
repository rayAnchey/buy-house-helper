package cn.deskie.sysinterface.service.business;

import cn.deskie.sysentity.entity.HouseDetail;
import cn.deskie.sysentity.entity.Project;

import java.util.List;

public interface HouseDetailService {
    int save(HouseDetail houseDetail);
    int update(HouseDetail houseDetail);
    int batchSave(List<HouseDetail> list);
    int saveExcelToDB(Project project);
}