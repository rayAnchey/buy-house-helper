package cn.deskie.sysinterface.service.system;

import cn.deskie.sysentity.entity.Menu;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Administrator
 *
 */
public interface MenuService {


	
	/**
	 * 增加菜单菜单
	 * @param menu
	 * @return
     */
	public void addMenu(Menu menu);
	
	/**
	 * 删除菜单
	 * @param menuid
	 * @return
     */
	public void deleteMenu(String menuid);
	
	/**
	 * 修改菜单
	 * @param menuid
	 * @return
     */
	public Menu findMenuById(String menuid);
	
	/**
	 * 修改菜单
	 * @param
	 * @return
     */
	public void update(Menu menu);
	
	
	/**
	 * 查询上级菜单
	 * @return
	 */
	
	 Menu findparent(String menuId);
	
	 List<Menu> findMenuIdByRoleId(String roleId);

}
