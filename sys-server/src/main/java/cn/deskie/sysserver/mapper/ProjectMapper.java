package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Project;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(Project record);

    Project selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    int batchSave(List list);
}