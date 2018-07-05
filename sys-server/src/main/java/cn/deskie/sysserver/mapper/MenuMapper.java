package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Menu;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);
}