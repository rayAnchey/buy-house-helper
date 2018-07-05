package cn.deskie.sysentity.entity;

import java.io.Serializable;

public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -4491925671511379913L;

    private String roleId;

    private String menuId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}