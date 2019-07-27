package com.temp.permission.model.dto.param;

import java.util.List;

public class ParamActionDTO {
    private Integer parentId;
    private List<String> resources;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
