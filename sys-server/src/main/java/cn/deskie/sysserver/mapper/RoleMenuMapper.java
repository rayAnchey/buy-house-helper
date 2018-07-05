package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.RoleMenu;

public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);
}