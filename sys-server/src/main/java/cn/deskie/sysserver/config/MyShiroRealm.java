package cn.deskie.sysserver.config;

import cn.deskie.syscommon.utils.EncryptUtil;
import cn.deskie.sysentity.entity.Menu;
import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.User;
import cn.deskie.sysinterface.service.system.MenuService;
import cn.deskie.sysinterface.service.system.RoleService;
import cn.deskie.sysinterface.service.system.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        User user = (User) principals.getPrimaryPrincipal();
        if(null==user){
            return null;
        }
        List<Role> roleList = user.getRoleList();
        Set<String> roles = new HashSet<>();
        Set<String> urlSet = new HashSet<String>();
        for (Role role : roleList) {
            roles.add(role.getId());
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
        info.addRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 校验用户名密码
        String password=String.copyValueOf(token.getPassword());
        User user = userService.findUserByLoginName(token.getUsername());

        // 账号不存在
        if (null == user) {
            throw new UnknownAccountException();
        }
        if(!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException();
        }
        // 账号未启用
        if (User.STATUS_DISABLE.equals(user.getStatus())) {
            throw new DisabledAccountException("帐号已经禁止登录");
        }
        List<Role> roleList = roleService.findRoleIdListByUserId(user.getId());
        user.setRoleList(roleList);
        SecurityUtils.getSubject().getSession().setAttribute("user",user);
        // 注意此处的返回值没有使用加盐方式,如需要加盐，可以在密码参数上加
        return new SimpleAuthenticationInfo(user, token.getPassword(), token.getUsername());
    }
}