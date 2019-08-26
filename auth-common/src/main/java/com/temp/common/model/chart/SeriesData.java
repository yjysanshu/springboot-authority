package com.temp.common.model.chart;

import java.util.List;
import java.util.Map;

public class SeriesData {
    private String name;
    private String type;
    private String stack;
    private Map<String, Object> label;
    private List<Double> data;

    public SeriesData() {

    }

    public SeriesData(String name, String type, String stack, Map<String, Object> label) {
        this.name = name;
        this.type = type;
        this.stack = stack;
        this.label = label;
    }

    public SeriesData(String name, String type, String stack, Map<String, Object> label, List<Double> data) {
        this.name = name;
        this.type = type;
        this.stack = stack;
        this.label = label;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public Map<String, Object> getLabel() {
        return label;
    }

    public void setLabel(Map<String, Object> label) {
        this.label = label;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }
}
