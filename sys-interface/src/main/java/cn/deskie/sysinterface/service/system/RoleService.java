package cn.deskie.sysinterface.service.system;


import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.RoleMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanglei on 2017/12/21.
 * @author Administrator
 */
public interface RoleService {

    /**
     * 根据用户id查询角色id列表
     * @param userId
     * @return
     */
    List<Role> findRoleIdListByUserId(String userId);

    /**
     * 新增角色
     * @param role
     * @return
     */
    Integer addRole(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    Integer editRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Integer deleteRole(String id);

    /**
     * 根据id查找role
     * @param id
     * @return
     */
    Role findRoleById(String id);
    


    /**
     * 更新角色拥有的菜单
     */
    void grantMenu(List<RoleMenu> list, String roleId);

    /**
     * 判断角色是否有用户已分配，若已分配则不能执行删除
     * @param roleId
     * @return
     */
    boolean roleIsUsed(String roleId);

    /**
     * 判断角色名是否存在
     * @param roleName
     * @return
     */
    boolean isRoleNameExist(String roleName);
}
