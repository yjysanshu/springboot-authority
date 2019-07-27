package com.temp.permission.model.request;

import java.util.List;

public class RoleUserRequest extends BaseRequest {
	private Integer id;
	private Integer roleId;
	private Integer userId;

	private List<Integer> list;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}
}