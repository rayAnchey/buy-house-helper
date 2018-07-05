package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Role;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
}