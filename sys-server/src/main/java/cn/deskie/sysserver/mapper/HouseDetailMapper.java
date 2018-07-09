package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.HouseDetail;

import java.util.List;

public interface HouseDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(HouseDetail record);

    int insertSelective(HouseDetail record);

    HouseDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HouseDetail record);

    int updateByPrimaryKey(HouseDetail record);

    int batchSave(List<HouseDetail> list);
}