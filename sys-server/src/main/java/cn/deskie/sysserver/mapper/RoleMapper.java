package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.RoleMenu;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    //
    List<Role> findRoleIdListByUserId(String id);

    int  update(Role role);

    int delete(String id);

    Role findRoleById(String id);
    void grantMenu(List<RoleMenu> list, String roleId);
    boolean roleIsUsed(String id);
    Role getByName(String roleName);
}