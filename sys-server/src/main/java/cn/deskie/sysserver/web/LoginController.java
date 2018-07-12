package cn.deskie.sysserver.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String loginName, String password, String checkCode, HttpSession session){
        return "main";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(){
        return "login";
    }

}