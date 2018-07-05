package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.HouseDetail;

public interface HouseDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(HouseDetail record);

    int insertSelective(HouseDetail record);

    HouseDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HouseDetail record);

    int updateByPrimaryKey(HouseDetail record);
}