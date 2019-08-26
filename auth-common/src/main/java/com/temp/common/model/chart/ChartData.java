package com.temp.common.model.chart;

import java.util.List;
import java.util.Map;

public class ChartData {
    private Map<String, String> title;
    private List<String> color;
    private Map<String, Object> tooltip;
    private Map<String, List<String>> legend;
    private Boolean calculable;
    private Map<String, Object> toolbox;
    private List<AliasData> XAxis;
    private List<Map> YAxis;
    private List dataZoom;
    private List<SeriesData> series;

    public Map<String, String> getTitle() {
        return title;
    }

    public void setTitle(Map<String, String> title) {
        this.title = title;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public Map<String, Object> getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map<String, Object> tooltip) {
        this.tooltip = tooltip;
    }

    public Map<String, List<String>> getLegend() {
        return legend;
    }

    public void setLegend(Map<String, List<String>> legend) {
        this.legend = legend;
    }

    public Boolean getCalculable() {
        return calculable;
    }

    public void setCalculable(Boolean calculable) {
        this.calculable = calculable;
    }

    public Map<String, Object> getToolbox() {
        return toolbox;
    }

    public void setToolbox(Map<String, Object> toolbox) {
        this.toolbox = toolbox;
    }

    public List<AliasData> getXAxis() {
        return XAxis;
    }

    public void setXAxis(List<AliasData> XAxis) {
        this.XAxis = XAxis;
    }

    public List<Map> getYAxis() {
        return YAxis;
    }

    public void setYAxis(List<Map> YAxis) {
        this.YAxis = YAxis;
    }

    public List getDataZoom() {
        return dataZoom;
    }

    public void setDataZoom(List dataZoom) {
        this.dataZoom = dataZoom;
    }

    public List<SeriesData> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesData> series) {
        this.series = series;
    }
}
