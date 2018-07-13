package cn.deskie.sysserver.web;

import cn.deskie.syscommon.utils.EncryptUtil;
import cn.deskie.syscommon.vo.ResultCode;
import cn.deskie.sysentity.entity.Role;
import cn.deskie.sysentity.entity.User;
import cn.deskie.sysinterface.service.system.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static String MAIN_PAGE = "main";
    private static String LOGIN_PAGE = "login";
    private static String REGISTER_PAGE = "register" ;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(){
        return LOGIN_PAGE;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model){
        //已经登录过，直接进入主页
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            Object authorized = subject.getSession().getAttribute("isAuthorized");
            if (authorized != null && Boolean.valueOf(authorized.toString()))
                return MAIN_PAGE;
        }
        String userName = request.getParameter("userName");
        //默认首页，第一次进来
        String password = request.getParameter("password");
        //密码加密+加盐
        password = EncryptUtil.getPassword(password, userName);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        subject = SecurityUtils.getSubject();
        String msg;
        try {
            subject.login(token);
            //通过认证
            if (subject.isAuthenticated()) {
                User user = (User) subject.getPrincipal();
                List<Role> roles = user.getRoleList();
                boolean isPermitted = subject.isPermitted("user");
                if (!roles.isEmpty()) {
                    subject.getSession().setAttribute("isAuthorized", true);
                    return MAIN_PAGE;
                } else {//没有授权
                    msg = "您没有得到相应的授权！";
                    model.addAttribute("message", new ResultCode("1", msg));
                    subject.getSession().setAttribute("isAuthorized", false);
                    logger.error(msg);
                    return LOGIN_PAGE;
                }

            } else {
                return LOGIN_PAGE;
            }
            //0 未授权 1 账号问题 2 密码错误  3 账号密码错误
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect";
            model.addAttribute("message", new ResultCode("2", msg));
            logger.error(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", new ResultCode("3", msg));
            logger.error(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", new ResultCode("1", msg));
            logger.error(msg);
        }
        return LOGIN_PAGE;
    }
    @RequestMapping(value = "/logout")
    private String doLogout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String toRegister(){
        return REGISTER_PAGE;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(User user,Model model){
        user.setPassword(EncryptUtil.getPassword(user.getPassword(), user.getEmail()));
        String userId = userService.save(user);
        //关联一般用户权限
//        userRoleService.setRoleForRegisterUser(userId);
        //关联成功后登陆
        return loginByAuth(user);
    }

    /**
     * 校验当前登录名/邮箱的唯一性
     *
     * @param loginName 登录名
     * @param userId    用户ID（用户已经存在，改回原来的名字还是唯一的）
     * @return
     */
    @RequestMapping(value = "/checkUnique", method = RequestMethod.POST)
    @ResponseBody
    public Map checkExist(String loginName, String userId) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        User user = userService.findUserByLoginName(loginName);
        //用户不存在，校验有效
        if (user == null) {
            map.put("valid", true);
        } else {
            if (!StringUtils.isEmpty(userId) && userId.equals(user.getEmail())) {
                map.put("valid", true);
            } else {
                map.put("valid", false);
            }
        }
        return map;
    }
    public String loginByAuth(User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        //通过认证
        if (subject.isAuthenticated()) {
            return MAIN_PAGE;
        } else {
            return LOGIN_PAGE;
        }
    }
}