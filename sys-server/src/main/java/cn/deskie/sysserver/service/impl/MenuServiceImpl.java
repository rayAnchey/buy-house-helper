package cn.deskie.sysserver.service.impl;

import cn.deskie.sysentity.entity.Menu;
import cn.deskie.sysinterface.service.system.MenuService;
import cn.deskie.sysserver.mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class MenuServiceImpl implements MenuService {

	private Logger logger= Logger.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuMapper menuMapper;




	@Override
	public void addMenu(Menu menu){
		menuMapper.insert(menu);
	}
	@Override
	public void deleteMenu(String menuid) {
		menuMapper.delete(menuid);
	}

	@Override
	public Menu findMenuById(String menuid) {
		return menuMapper.findById(menuid);
	}

	@Override
	public void update(Menu menu) {
		menuMapper.update( menu);
	}

	@Override
	public List<Menu> findMenuIdByRoleId(String roleId) {
		return menuMapper.findMenuByRoleId(roleId);
	}

	@Override
	public Menu findparent(String id) {
		return menuMapper.findParent(id);
	}


}
