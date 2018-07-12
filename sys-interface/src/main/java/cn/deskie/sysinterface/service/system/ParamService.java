package cn.deskie.sysinterface.service.system;

import cn.deskie.sysentity.entity.Param;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 
 * @author gyj
 * 参数service类
 */
public interface ParamService{
	
	
	/**
     * 根据参数id查找参数信息
     * @return
     */
	public Param findParamByID(String paramid);
	
	/**
     * 保存参数信息
     * @param param
     * @return
     */
	public void save(Param param);
	
	/**
     * 删除参数信息
     * @param paramid
     * @return
     */
	public void delete(String paramid);
	
	/**
     * 更新参数信息
     * @param param
     * @return
     */
	public void updata(Param param);
	

}
