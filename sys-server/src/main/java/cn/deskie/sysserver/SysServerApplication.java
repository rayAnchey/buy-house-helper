package cn.deskie.sysserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "cn.deskie.sysserver.mapper")
@EnableTransactionManagement
@EnableAutoConfiguration
public class SysServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysServerApplication.class, args);
	}
}
