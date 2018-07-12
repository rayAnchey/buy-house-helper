package cn.deskie.sysserver.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Component
public class DeleteAttrFilesTask {

    @Value("storePath")
    private String path;

    /**
     * 附件删除定时任务，删除创建时间距现在一个月之外的zip和解压文件夹
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void start(){
        File dir = new File(path);
        File[] files = dir.listFiles();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        Date date = calendar.getTime();
        for(File file:files){
            Date createDate = new Date(file.lastModified());
            if(createDate.before(date)){
                delete(file);
            }
        }

    }


    public void delete(File file){
        if(file.isDirectory()){
            File[] subFiles = file.listFiles();
            for(File subFile:subFiles) {
                delete(subFile);
            }
            file.delete();
        }else {
            file.delete();
        }
    }
}