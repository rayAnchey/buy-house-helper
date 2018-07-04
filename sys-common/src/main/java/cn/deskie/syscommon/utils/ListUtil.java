package cn.deskie.syscommon.utils;

import java.util.List;

public class ListUtil {
    public static boolean isNotEmpty(List list){
        if(null!=list&&list.size()>0){
            return true;
        }else {
            return false;
        }
    }

}