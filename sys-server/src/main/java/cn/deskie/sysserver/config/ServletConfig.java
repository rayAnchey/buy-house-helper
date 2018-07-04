package cn.deskie.sysserver.config;

import cn.deskie.sysserver.utils.SystemFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * platform：
 * Author：zhanglei
 * createTime： 2018/7/1 17:40
 * version：1.0
 * description：
 */
@Configuration
public class ServletConfig {

    @Bean
    public FilterRegistrationBean systemFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new SystemFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
