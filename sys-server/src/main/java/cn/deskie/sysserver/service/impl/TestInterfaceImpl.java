package cn.deskie.sysserver.service.impl;

import cn.deskie.sysentity.User;
import cn.deskie.sysinterface.service.TestInterface;
import cn.deskie.sysserver.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * platform：
 * Author：zhanglei
 * createTime： 2018/7/1 15:03
 * version：1.0
 * description：
 */
@Service
public class TestInterfaceImpl implements TestInterface {

    private static final Logger logger = LoggerFactory.getLogger(TestInterfaceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return null;
    }
}
