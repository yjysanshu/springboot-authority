package com.temp.permission.model.response;

import com.temp.permission.model.MenuBase;

import java.util.List;

public class MenuResponse extends MenuBase {
    private Boolean checked;
    private List<MenuResponse> children;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<MenuResponse> getChildren() {
        return children;
    }

    public void setChildren(List<MenuResponse> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + this.getId() +
                ", url='" + this.getUrl() + '\'' +
                ", parentId=" + this.getParentId() +
                ", title='" + this.getTitle() + '\'' +
                ", path='" + this.getPath() + '\'' +
                ", seq=" + this.getSeq() +
                ", icon='" + this.getIcon() + '\'' +
                ", type='" + this.getType() + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}
