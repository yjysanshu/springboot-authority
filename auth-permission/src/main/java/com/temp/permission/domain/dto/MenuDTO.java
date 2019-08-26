package com.temp.permission.domain.dto;

import lombok.Data;

@Data
public class MenuDTO {
    private Integer id;
    private String url;
    private Integer parentId;
    private String title;
    private String path;
    private Integer seq;
    private String icon;
    private String type;
}
