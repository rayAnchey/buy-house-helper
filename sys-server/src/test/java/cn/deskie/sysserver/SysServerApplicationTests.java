package cn.deskie.sysserver;

import cn.deskie.syscommon.excel.ImportExcel;
import cn.deskie.syscommon.utils.IdGen;
import cn.deskie.sysentity.entity.HouseDetail;
import cn.deskie.sysentity.entity.Project;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

	public static void main(String[] args) throws Exception{
		File file = new File("D:\\opt\\filedown\\20180518160148_315_2836375\\商品住房价格公示项目名单（2018年第23批）（总第三十五批）\\5、御景·水岸花园（桃源漫步）6#.xlsx");
		ImportExcel ei = new ImportExcel(file, 2, 0);
		List<HouseDetail> list = ei.getDataList(HouseDetail.class);
		List<HouseDetail> newList = new ArrayList<>();
		for(HouseDetail houseDetail :list){
			houseDetail.setProjectId("projectId");
			houseDetail.setProjectName("projectName");
			houseDetail.setId(IdGen.uuid());
			houseDetail.setPublicTime(new Date());
			houseDetail.setBuindNo("8#".replace("#",""));
			newList.add(houseDetail);
		}
		System.out.println(newList);
	}
	@Test
	public void test1() throws InterruptedException{
		List list = Collections.synchronizedList(new ArrayList<>());
		class Runner implements Runnable{

			@Override
			public void run() {
				for(int i=0;i<1000;i++){
					list.add(UUID.randomUUID());
				}
				System.out.println(list.size());
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("");
			}
		}
		Runnable runnable = () -> {
			for(int i=0;i<1000;i++){
				list.add(i);
			}
			System.out.println(list.size());
        };
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		thread1.start();
		thread2.start();
		thread3.start();
		System.out.println("final:"+list.size());
	}

}
