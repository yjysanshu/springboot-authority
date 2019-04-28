package com.temp.permission.entity;

import java.util.Date;

public class Privilege {
    private Integer privilegeId;
    private Integer privilegeResourceId;
    private Integer privilegeRoleId;
    private Date privilegeCreateAt;
    private Date privilegeUpdateAt;

    private Role role;
    private Resource resource;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Integer getPrivilegeResourceId() {
        return privilegeResourceId;
    }

    public void setPrivilegeResourceId(Integer privilegeResourceId) {
        this.privilegeResourceId = privilegeResourceId;
    }

    public Integer getPrivilegeRoleId() {
        return privilegeRoleId;
    }

    public void setPrivilegeRoleId(Integer privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    public Date getPrivilegeCreateAt() {
        return privilegeCreateAt;
    }

    public void setPrivilegeCreateAt(Date privilegeCreateAt) {
        this.privilegeCreateAt = privilegeCreateAt;
    }

    public Date getPrivilegeUpdateAt() {
        return privilegeUpdateAt;
    }

    public void setPrivilegeUpdateAt(Date privilegeUpdateAt) {
        this.privilegeUpdateAt = privilegeUpdateAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
