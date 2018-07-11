package cn.deskie.sysserver.service.impl;

import cn.deskie.syscommon.excel.ImportExcel;
import cn.deskie.syscommon.utils.IdGen;
import cn.deskie.sysentity.entity.Batch;
import cn.deskie.sysentity.entity.Project;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysinterface.service.business.ProjectService;
import cn.deskie.sysserver.mapper.ProjectMapper;
import cn.deskie.sysserver.rocketmq.RocketMQServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private RocketMQServer rocketMQServer;

    @Autowired
    private BatchService batchService;

    @Override
    public int save(Project project) {
        return projectMapper.insert(project);
    }

    @Override
    public int update(Project project) {
        return projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public void saveExcelToDB(Batch batch) {
        File file = new File(batch.getAttachmentName().replace(".zip",""));
        File[] xlsxs = file.listFiles();
        for(File f:xlsxs){
            if(f.getAbsolutePath().endsWith(".xlsx")){
                file = f;
            }
        }
        try {
            if(file.exists()){
                ImportExcel ei = new ImportExcel(file, 1, 0);
                List<Project> list = ei.getDataList(Project.class);
                String projectType = "";
                List<Project> newList = new ArrayList<>();
                String projectName = "";
                String developer = "";
                String address = "";
                for(Project project:list){
                    if("毛坯项目".equals((project.getId()+"").trim())){
                        projectType = "0";
                    }else if("精装项目".equals((project.getId()+"").trim())){
                        projectType = "1";
                    }else {
                        project.setId(IdGen.uuid());
                        //处理2行项目一样 楼栋号不一样时，下一行继承上一行的项目属性
                        if(StringUtils.isNotBlank(project.getProjectName())){
                            projectName = (project.getProjectName());
                            developer = (project.getDeveloper());
                            address = (project.getAddress());
                        }else {
                            project.setProjectName(projectName);
                            project.setDeveloper(developer);
                            project.setAddress(address);
                        }
                        project.setProjectType(projectType);
                        project.setBatchNo(batch.getBatchNo());
                        project.setBatchId(batch.getId());
                        project.setPublicTime(batch.getPublicTime());
                        project.setAddTime(new Date());
                        newList.add(project);
                    }
                }
                projectMapper.batchSave(newList);
                //更新批次处理字段为已处理
                batch.clearProperties().setIsResolved("1");
                batchService.update(batch);
                //继续处理
                for(Project project:newList){
                    rocketMQServer.sendMessage(project);
                }
            }else {
                logger.error("project excel 文件不存在："+file.getAbsolutePath());
            }

        }catch (Exception e){
            logger.error("project excel 解析出错！"+e.getMessage());
        }
    }

    @Override
    public int batchSave(List<Project> list) {
        return projectMapper.batchSave(list);
    }

    @Override
    public Project getById(String id) {
        return projectMapper.selectByPrimaryKey(id);
    }
}