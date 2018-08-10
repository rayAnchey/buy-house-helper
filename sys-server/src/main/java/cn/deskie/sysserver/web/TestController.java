package cn.deskie.sysserver.web;

import cn.deskie.sysentity.User;
import cn.deskie.sysentity.entity.Project;
import cn.deskie.sysinterface.service.TestInterface;
import cn.deskie.sysinterface.service.business.BatchService;
import cn.deskie.sysinterface.service.business.HouseDetailService;
import cn.deskie.sysinterface.service.business.ProjectService;
import cn.deskie.sysserver.rocketmq.RocketMQServer;
import cn.deskie.sysserver.task.ResolveExcelTask;
import cn.deskie.sysserver.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestInterface testInterface;
    @Autowired
    private BatchService batchService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private HouseDetailService houseDetailService;
    @Autowired
    private RocketMQServer rocketMQServer;

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
    @RequestMapping("/startBatch")
    public String startBatch(HttpServletRequest request,Model model){
        batchService.startCrawlerTask();
        return "main";
    }
    @RequestMapping("/startdownload")
    public String startdownload(){
//        batchService.downLoadAttachments();
        ResolveExcelTask resolveExcelTask = (ResolveExcelTask) SpringContextUtil.getBean("resolveExcelTask");
        resolveExcelTask.start();
        return "main";
    }

    @RequestMapping("/startUnzip")
    public String startUnzi(){
//        batchService.unZipAndSaveExcelToDB();
        return "main";
    }
    @RequestMapping("/startHouseInfoTodb")
    public String startHouseInfoTodb(String id){
        Project project =projectService.getById(id);
        houseDetailService.saveExcelToDB(project);
        return "main";
    }

    @RequestMapping("/sendmq")
    public String sendmq(String tags){
//        rocketMQServer.sendMessage("时间："+new Date(),tags);
        return "main";
    }

}
