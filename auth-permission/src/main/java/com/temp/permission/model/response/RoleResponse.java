package com.temp.permission.model.response;

import java.util.List;

public class RoleResponse extends BaseResponse {
	private Integer id;
	private Integer parentId;
	private String name;
	private String desc;
	private Integer status;
	private Integer type;

	private Integer level;
	private Boolean isExpand;
	private Boolean isParent;
	private List<RoleResponse> children;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getIsExpand() {
		return isExpand;
	}

	public void setIsExpand(Boolean isExpand) {
		this.isExpand = isExpand;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public List<RoleResponse> getChildren() {
		return children;
	}

	public void setChildren(List<RoleResponse> children) {
		this.children = children;
	}
}