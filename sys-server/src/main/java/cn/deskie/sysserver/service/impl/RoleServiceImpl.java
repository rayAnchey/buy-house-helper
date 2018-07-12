package cn.deskie.sysserver.service.impl;

import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.RoleMenu;
import cn.deskie.sysinterface.service.system.RoleService;
import cn.deskie.sysserver.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglei on 2017/12/21.
 * @author Administrator
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> findRoleIdListByUserId(String userId) {
        return roleMapper.findRoleIdListByUserId(userId);
    }

    @Override
    public Integer addRole(Role role) {
        role.setAddTime(new Date());
        return roleMapper.insert(role);
    }

    @Override
    public Integer editRole(Role role) {
        role.setUpdateTime(new Date());
        return roleMapper.update(role);
    }

    @Override
    public Integer deleteRole(String id) {
        return roleMapper.delete(id);
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }


    @Override
    public void grantMenu(List<RoleMenu> list, String roleId) {
        roleMapper.grantMenu(list,roleId);
    }

    @Override
    public boolean roleIsUsed(String roleId) {
        return roleMapper.roleIsUsed(roleId);
    }

    @Override
    public boolean isRoleNameExist(String roleName) {
        Role role=roleMapper.getByName(roleName);
        if(null==role){
            return false;
        }
        return true;
    }
}
