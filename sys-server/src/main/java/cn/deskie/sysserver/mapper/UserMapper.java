package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //
    User findUserByLoginName(String loginName);
    boolean guare(String[] pl, String userId);
}