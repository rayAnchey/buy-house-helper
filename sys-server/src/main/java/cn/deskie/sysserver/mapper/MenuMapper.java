package cn.deskie.sysserver.mapper;

import cn.deskie.sysentity.entity.Menu;

import java.util.List;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);
    //
    void delete(String id);
    List<Menu> findMenuByRoleId(String rollId);

    Menu findParent(String id);
    Menu findById(String id);
    int update(Menu menu);
}