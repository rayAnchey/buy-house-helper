package cn.deskie.sysinterface.service.system;

import cn.deskie.sysentity.entity.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 根据登陆名称查找用户
	 */
	User findUserByLoginName(String loginName);
	
	/**
	 * 删除用户
	 */
	boolean delete(String userId);
	/**
	 * 保存用户
	 */
	boolean save(User user);
	/**
	 * 修改用户信息
	 */
	boolean update(User user);
	/**
	 * 通过用户id获取用户
	 */
	User getByUserID(String userId);
	/**
	 * 用户授权
	 */
	boolean guare(String[] pl, String userId);


}
