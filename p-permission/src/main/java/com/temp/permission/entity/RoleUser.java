package com.temp.permission.entity;

import java.util.Date;

public class RoleUser {
    private Integer roleUserId;
    private Integer roleUserRoleId;
    private Integer roleUserUserId;
    private Date roleUserCreateAt;
    private Date roleUserUpdateAt;

    private User user;
    private Role role;

    public Integer getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Integer roleUserId) {
        this.roleUserId = roleUserId;
    }

    public Integer getRoleUserRoleId() {
        return roleUserRoleId;
    }

    public void setRoleUserRoleId(Integer roleUserRoleId) {
        this.roleUserRoleId = roleUserRoleId;
    }

    public Integer getRoleUserUserId() {
        return roleUserUserId;
    }

    public void setRoleUserUserId(Integer roleUserUserId) {
        this.roleUserUserId = roleUserUserId;
    }

    public Date getRoleUserCreateAt() {
        return roleUserCreateAt;
    }

    public void setRoleUserCreateAt(Date roleUserCreateAt) {
        this.roleUserCreateAt = roleUserCreateAt;
    }

    public Date getRoleUserUpdateAt() {
        return roleUserUpdateAt;
    }

    public void setRoleUserUpdateAt(Date roleUserUpdateAt) {
        this.roleUserUpdateAt = roleUserUpdateAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
