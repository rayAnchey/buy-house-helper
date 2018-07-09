package cn.deskie.sysserver.service.impl;

import cn.deskie.syscommon.excel.ImportExcel;
import cn.deskie.syscommon.utils.IdGen;
import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysentity.entity.HouseDetail;
import cn.deskie.sysentity.entity.Project;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysinterface.service.business.HouseDetailService;
import cn.deskie.sysserver.mapper.HouseDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HouseDetailServiceImpl implements HouseDetailService {

    private static final Logger logger = LoggerFactory.getLogger(HouseDetailServiceImpl.class);

    @Autowired
    private HouseDetailMapper houseDetailMapper;
    @Autowired
    private BatchService batchService;

    @Override
    public int save(HouseDetail houseDetail) {
        return houseDetailMapper.insert(houseDetail);
    }

    @Override
    public int update(HouseDetail houseDetail) {
        return houseDetailMapper.updateByPrimaryKeySelective(houseDetail);
    }

    @Override
    public int batchSave(List<HouseDetail> list) {
        return houseDetailMapper.batchSave(list);
    }

    @Override
    public int saveExcelToDB(Project project) {
        String dirPath = batchService.findById(project.getBatchId()).getAttachmentName().replace(".zip", "") + File.separator + "房源清单";
        File[] files = new File(dirPath).listFiles();
        File houseFile = null;
        String projectId = project.getId();
        String projectName = project.getProjectName();
        Date publicTime = project.getPublicTime();
        String[] builds = project.getBuildNo().split("、");
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            if (absolutePath.contains(project.getProjectName())) {
                for (String build : builds) {
                    if (absolutePath.contains(build)) {
                        try {
                            ImportExcel ei = new ImportExcel(f, 2, 0);
                            List<HouseDetail> list = ei.getDataList(HouseDetail.class);
                            List<HouseDetail> newList = new ArrayList<>();
                            for (HouseDetail houseDetail : list) {
                                houseDetail.setProjectId(projectId);
                                houseDetail.setProjectName(projectName);
                                houseDetail.setId(IdGen.uuid());
                                houseDetail.setPublicTime(publicTime);
                                houseDetail.setBuindNo(build.replace("#", ""));
                                houseDetail.setAddTime(new Date());
                                newList.add(houseDetail);
                            }
                            return newList.size() > 0 ? houseDetailMapper.batchSave(newList) : 0;
                        } catch (Exception e) {
                            logger.error("houseDetail excel 解析出错！" + e.getMessage());
                        }
                    }
                }

            }
        }
        return 0;
    }
}