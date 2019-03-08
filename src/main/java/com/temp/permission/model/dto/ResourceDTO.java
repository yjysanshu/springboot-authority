package com.temp.permission.model.dto;

import com.temp.permission.entity.Resource;
import com.temp.permission.model.MenuBase;

import java.util.List;

public class ResourceDTO extends MenuBase {
    private boolean checked;
    private List<ResourceDTO> children;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<ResourceDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDTO> children) {
        this.children = children;
    }
}
