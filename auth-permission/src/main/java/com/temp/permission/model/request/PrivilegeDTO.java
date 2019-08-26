package com.temp.permission.model.request;

import java.util.List;

public class PrivilegeDTO extends BaseRequest {
	private Integer roleId;
	private String resourceType;
	private List<Integer> resourceIds;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public List<Integer> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(List<Integer> resourceIds) {
		this.resourceIds = resourceIds;
	}
}