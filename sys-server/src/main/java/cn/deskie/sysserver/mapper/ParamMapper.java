package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Param;

public interface ParamMapper {
    int insert(Param record);

    int insertSelective(Param record);
}