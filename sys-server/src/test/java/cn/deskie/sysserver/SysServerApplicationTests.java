package cn.deskie.sysserver;

import cn.deskie.syscommon.excel.ImportExcel;
import cn.deskie.syscommon.utils.IdGen;
import cn.deskie.sysentity.entity.Project;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysServerApplicationTests {

	@Test
	public void contextLoads() throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
		File file = new File("D:\\opt\\git\\20180629182230_900_2886789\\商品住房价格公示项目名单（2018年第34批）（总第四十六批）.xlsx");
		ImportExcel ei = new ImportExcel(file, 1, 0);
		List<Project> list = ei.getDataList(Project.class);
		String projectType = "";
		List<Project> newList = new ArrayList<>();
		for(Project project :list){
			if("毛坯项目".equals((project.getId()+"").trim())){
				projectType = "0";
			}else if("精装项目".equals((project.getId()+"").trim())){
				projectType = "1";
			}else {
				project.setId(IdGen.uuid());
				project.setProjectType(projectType);
				newList.add(project);
			}
		}
		System.out.println(newList);
	}

}
