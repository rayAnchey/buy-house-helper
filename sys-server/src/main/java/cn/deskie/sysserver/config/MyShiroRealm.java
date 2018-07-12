package cn.deskie.sysserver.config;

import cn.deskie.sysentity.entity.Menu;
import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.User;
import cn.deskie.sysinterface.service.system.MenuService;
import cn.deskie.sysinterface.service.system.RoleService;
import cn.deskie.sysinterface.service.system.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {


    private static final Logger logger = Logger.getLogger(MyShiroRealm.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("*******************Shiro权限认证***********************");
        User user = (User) principals.getPrimaryPrincipal();
        List<Role> roleList = user.getRoleList();
        Set<String> urlSet = new HashSet<String>();
        for (Role role : roleList) {
            List<Menu> urlList = menuService.findMenuIdByRoleId(role.getId());
            if (urlList != null && urlList.size() > 0) {
                for (Menu menu: urlList) {
                    if(StringUtils.isNotBlank(menu.getMenuUrl())) {
                        urlSet.add(menu.getMenuUrl());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("*******************Shiro身份认证***********************");
        User user = userService.findUserByLoginName(authcToken.getPrincipal().toString());
        // 账号不存在
        if (null == user) {
            throw new AccountException("帐号不存在");
        }
        // 账号未启用
        if ("0".equals(user.getStatus())) {
            throw new DisabledAccountException("帐号已经禁止登录");
        }
        List<Role> roleList = roleService.findRoleIdListByUserId(user.getId());
        user.setRoleList(roleList);
        // 认证缓存信息
        return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
    }
}