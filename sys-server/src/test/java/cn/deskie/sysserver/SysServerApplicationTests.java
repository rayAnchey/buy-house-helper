package cn.deskie.sysserver;

import cn.deskie.syscommon.excel.ImportExcel;
import cn.deskie.sysentity.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysServerApplicationTests {

	@Test
	public void contextLoads() throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
		File file = new File("D:\\opt\\git\\20180629182230_900_2886789\\商品住房价格公示项目名单（2018年第34批）（总第四十六批）.xlsx");
		ImportExcel ei = new ImportExcel(file, 1, 0,1);
		List<Project> list = ei.getDataList(Project.class);
		System.out.println(list);
	}

}
