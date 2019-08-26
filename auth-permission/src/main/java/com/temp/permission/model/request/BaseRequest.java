package com.temp.permission.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BaseRequest {
    private Integer limit = 10;
    private Integer currentPage = 1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Date getCreateAt() {
        return start;
    }

    public void setCreateAt(Date createAt) {
        this.start = createAt;
    }

    public Date getUpdateAt() {
        return end;
    }

    public void setUpdateAt(Date updateAt) {
        this.end = updateAt;
    }
}
