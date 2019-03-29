package com.temp.admin.dto.param;

public class StartEndParamDTO {
    private String start;
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "StartEndParamDTO{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
