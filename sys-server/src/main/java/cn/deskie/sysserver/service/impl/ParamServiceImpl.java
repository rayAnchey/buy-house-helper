package cn.deskie.sysserver.service.impl;

import cn.deskie.sysentity.entity.Param;
import cn.deskie.sysinterface.service.system.ParamService;
import cn.deskie.sysserver.mapper.ParamMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author gyj
 * 系统参数service实现类
 */

@Service
public class ParamServiceImpl implements ParamService {

	private static Logger logger = LoggerFactory.getLogger(ParamServiceImpl.class);
	@Autowired
	private ParamMapper paramMapper;

	/**
     * 根据参数id删除参数信息
     * @param paramid
     * @return
     */
	@Override
	public void delete(String paramid) {
		paramMapper.delete(paramid);
	}

	/**
     * 新增参数信息
     * @param param
     * @return
     */
	@Override
	public void save(Param param) {
		paramMapper.insert(param);
	}

	/**
     * 根据参数id查找参数信息
     * @param paramid
     * @return
     */
	@Override
	public Param findParamByID(String paramid) {
		return paramMapper.findById(paramid);
	}

	/**
     * 更新参数信息
     * @param param
     * @return
     */
	@Override
	public void updata(Param param) {
		paramMapper.update(param);
	}
	
}