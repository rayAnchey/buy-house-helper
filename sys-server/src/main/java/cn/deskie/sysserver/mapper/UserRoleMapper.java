package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}