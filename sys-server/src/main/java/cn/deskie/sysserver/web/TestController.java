package cn.deskie.sysserver.web;

import cn.deskie.sysentity.User;
import cn.deskie.sysinterface.service.TestInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * platform：
 * Author：zhanglei
 * createTime： 2018/7/1 14:52
 * version：1.0
 * description：
 */
@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestInterface testInterface;

    @RequestMapping("/json")
    @ResponseBody
    public Object testJson(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("code","1");
        map.put("msg","success");
        return map;
    }

    @RequestMapping("/getAllUser")
    public String getAllUser(Model model){
        logger.info("进入controller。。。。");
        List<User> list = testInterface.findAll();
        model.addAttribute("userList",list);
        return "test";
    }
    @RequestMapping("/")
    public String toMain(HttpServletRequest request,Model model){
        String basePath = request.getContextPath();
        model.addAttribute("basePath",basePath);
        return "main";
    }

}
