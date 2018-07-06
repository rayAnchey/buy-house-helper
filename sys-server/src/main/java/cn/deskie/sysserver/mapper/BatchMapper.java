package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Batch;

import java.util.List;

public interface BatchMapper {
    int deleteByPrimaryKey(String id);

    int insert(Batch record);

    int insertSelective(Batch record);

    Batch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Batch record);

    int updateByPrimaryKey(Batch record);

    int batchSave(List<Batch> list);
}