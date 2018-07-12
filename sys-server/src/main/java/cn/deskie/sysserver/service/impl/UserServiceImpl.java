package cn.deskie.sysserver.service.impl;

import cn.deskie.sysentity.entity.User;
import cn.deskie.sysinterface.service.system.UserService;
import cn.deskie.sysserver.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findUserByLoginName(String loginName) {

        return userMapper.findUserByLoginName(loginName);
    }


    @Override
    public boolean delete(String id) {
        return userMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) == 1 ? true : false;
    }

    @Override
    public User getByUserID(String id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public boolean update(User user) {
        return userMapper.updateByPrimaryKeySelective(user) == 1 ? true : false;
    }

    @Override
    public boolean guare(String[] pl, String userId) {
        return userMapper.guare(pl, userId);
    }


}
