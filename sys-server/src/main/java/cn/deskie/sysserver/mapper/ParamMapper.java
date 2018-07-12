package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Param;

public interface ParamMapper {
    int insert(Param record);

    int insertSelective(Param record);
//
    int delete(String id);

    Param findById(String id);

    int update(Param param);
}