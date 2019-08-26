package com.temp.common.service.chart;

import com.temp.common.model.chart.ChartData;
import com.temp.common.util.ChartUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseChartService {

    //画图的类型
    protected String type;

    //画图的数据
    protected ChartData data = new ChartData();

    /**
     * 获取形成图表的数据
     * @return map
     */
    public ChartData handle()  {
        return this.title()
            .legend()
            .xAxis()
            .series()
            .dataZoom()
            .tooltip()
            .toolbox()
            .color()
            .getData();
    }

    /**
     * 获取图形的标题
     * @return string
     */
    protected abstract String getTitle();

    /**
     * 获取legend
     * @return this
     */
    protected abstract BaseChartService legend();

    /**
     * 获取横坐标的信息xAlias.data
     * @return this
     */
    protected abstract BaseChartService xAxis();

    /**
     * 获取图的显示信息
     * @return this
     */
    protected abstract BaseChartService series();

    /**
     * 图表标题信息
     * @return $this
     */
    protected BaseChartService title() {
        Map<String, String> map = new HashMap<>();
        map.put("text", this.getTitle());
        this.data.setTitle(map);
        return this;
    }

    /**
     * 获取动态图的效果
     * @return $this
     */
    protected BaseChartService dataZoom() {
        this.data.setDataZoom(new ArrayList<>());
        return this;
    }

    /**
     * 获取工具提示信息
     * @return $this
     */
    protected BaseChartService tooltip() {
        Map<String, String> apMap = new HashMap<>();
        apMap.put("type", "shadow");

        Map<String, Object> map = new HashMap<>();
        map.put("trigger", "axis");
        map.put("axisPointer", apMap);

        this.data.setTooltip(map);

        return this;
    }

    /**
     * 获取图表工具栏
     * @return $this
     */
    protected BaseChartService toolbox() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> featureMap = new HashMap<>();
        Map<String, Boolean> markMap = new HashMap<>();
        Map<String, Object> magicTypeMap = new HashMap<>();
        Map<String, Boolean> restoreMap = new HashMap<>();
        Map<String, Boolean> saveAsImageMap = new HashMap<>();
        saveAsImageMap.put("show", true);
        restoreMap.put("show", true);
        markMap.put("show", true);
        magicTypeMap.put("show", true);
        magicTypeMap.put("type", Arrays.asList("line", "bar", "stack", "tiled"));

        featureMap.put("mark", markMap);
        featureMap.put("magicType", magicTypeMap);
        featureMap.put("restore", restoreMap);
        featureMap.put("saveAsImage", saveAsImageMap);

        map.put("show", true);
        map.put("orient", "vertical");
        map.put("left", "right");
        map.put("top", "center");
        map.put("feature", featureMap);
/**
        [
            'show' => true,
            'orient' =>'vertical',
            'left' =>'right',
            'top' =>'center',
            'feature' => [
                'mark' => [
                    'show' => true
                ],
                'magicType' =>[
                    'show' =>true,
                    'type' => ['line', 'bar', 'stack', 'tiled']
                ],
                'restore' =>[
                    'show' =>true
                ],
                'saveAsImage' => [
                    'show' => true
                ]
            ],
        ];*/
        this.data.setToolbox(map);
        return this;
    }

    /**
     * 获取color
     * @return $this
     */
    protected BaseChartService color() {
        this.data.setColor(ChartUtil.getColor(this.data.getLegend().get("data").size()));
        return this;
    }

    /**
     * 返回数据
     * @return array
     */
    private ChartData getData() {
        return this.data;
    }

    /**
     * labelOption
     * @return map
     */
    protected Map<String, Object> getLabelOptionMap() {
        /**
         [
         'normal' => [
         'show' => true,
         'position' => 'insideBottom',
         'distance' => 15,
         'align' => 'left',
         'verticalAlign' => 'middle',
         'rotate' => 90,
         'formatter' =>'',
         'fontSize' =>16,
         'rich' => [
         'name' => [
         'textBorderColor' => '#fff'
         ]
         ],
         ],
         ];
         */
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> normalMap = new HashMap<>();
        Map<String, Object> richMap = new HashMap<>();
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("textBorderColor", "#fff");

        richMap.put("name", nameMap);

        normalMap.put("show", true);
        normalMap.put("position", "insideBottom");
        normalMap.put("distance", 15);
        normalMap.put("align", "left");
        normalMap.put("verticalAlign", "middle");
        normalMap.put("rotate", 90);
        normalMap.put("formatter", "");
        normalMap.put("fontSize", 16);
        normalMap.put("rich", richMap);

        map.put("normal", normalMap);

        return map;
    }
}
