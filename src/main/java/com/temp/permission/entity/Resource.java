package com.temp.permission.entity;

import java.util.Date;
import java.util.List;

public class Resource {
    private Integer resourceId;
    private Integer resourceParentId;
    private String resourceTarget;
    private Integer resourceType;
    private String resourceData;
    private Date resourceCreateAt;
    private Date resourceUpdateAt;

    private List<Role> roleList;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceParentId() {
        return resourceParentId;
    }

    public void setResourceParentId(Integer resourceParentId) {
        this.resourceParentId = resourceParentId;
    }

    public String getResourceTarget() {
        return resourceTarget;
    }

    public void setResourceTarget(String resourceTarget) {
        this.resourceTarget = resourceTarget;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceData() {
        return resourceData;
    }

    public void setResourceData(String resourceData) {
        this.resourceData = resourceData;
    }

    public Date getResourceCreateAt() {
        return resourceCreateAt;
    }

    public void setResourceCreateAt(Date resourceCreateAt) {
        this.resourceCreateAt = resourceCreateAt;
    }

    public Date getResourceUpdateAt() {
        return resourceUpdateAt;
    }

    public void setResourceUpdateAt(Date resourceUpdateAt) {
        this.resourceUpdateAt = resourceUpdateAt;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", resourceTarget='" + resourceTarget + '\'' +
                '}';
    }
}
