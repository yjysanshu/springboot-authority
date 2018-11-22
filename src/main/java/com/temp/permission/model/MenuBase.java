package com.temp.permission.model;

public class MenuBase {
    private Integer id;
    private String url;
    private Integer parentId;
    private String title;
    private String path;
    private Integer seq;
    private String icon;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MenuBase{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", seq=" + seq +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
