package com.temp.permission.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MenuVO {
    private Integer id;
    private String url;
    private Integer parentId;
    private String title;
    private String path;
    private Integer seq;
    private String icon;
    private String type;

    private Boolean checked;
    private List<MenuVO> children;
}
