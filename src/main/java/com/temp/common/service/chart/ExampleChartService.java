package com.temp.common.service.chart;

import com.temp.admin.dto.param.StartEndParamDTO;
import com.temp.common.model.chart.AliasData;
import com.temp.common.model.chart.SeriesData;
import com.temp.common.util.DateUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExampleChartService extends BaseChartService {

    private StartEndParamDTO paramDTO;

    protected String type = "line";

    private static List<String> typeList = Arrays.asList("e1", "e2");

    private static Map<String, String> typeMap = new HashMap<>();
    static {
        typeMap.put("e1", "样例1");
        typeMap.put("e2", "样例2");
    }

    /**
     * 这个处理是为了可以根据参数来查询生成图表的信息
     * @param dto dto
     * @return BaseChartService
     */
    public BaseChartService initParam(StartEndParamDTO dto) {
        this.paramDTO = dto;
        return this;
    }

    @Override
    protected String getTitle() {
        return "图形统计样例";
    }

    @Override
    protected BaseChartService legend() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (String type : typeList) {
            list.add(typeMap.get(type));
        }
        map.put("data", list);
        this.data.setLegend(map);
        return this;
    }

    @Override
    protected BaseChartService xAxis() {
        List<AliasData> list = new ArrayList<>();
        AliasData xAlias = new AliasData();
        xAlias.setType("category");
        xAlias.setBoundaryGap(false);
        xAlias.setData(DateUtil.getContinuousDate(paramDTO.getStart(), paramDTO.getEnd()));
        list.add(xAlias);
        this.data.setXAxis(list);
        this.data.setYAxis(new ArrayList<>());
        return this;
    }

    @Override
    protected BaseChartService series() {
        List<SeriesData> list = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("start", this.paramDTO.getStart());
        paramMap.put("end", this.paramDTO.getEnd());

        List<Map<String, Object>> mapList = initData(paramMap);

        for (Map statistic : mapList) {
            String key = statistic.get("date").toString() + statistic.get("type").toString();
            map.put(key, Double.valueOf((Double) statistic.get("total")));
        }

        for (String type : typeList) {
            List<Double> data = new ArrayList<>();
            for (String date : this.data.getXAxis().get(0).getData()) {
                String key = date + type;
                data.add(map.get(key) == null ? 0d : map.get(key));
            }

            list.add(new SeriesData(typeMap.get(type), "line", "", getLabelOptionMap(), data));
        }

        this.data.setSeries(list);
        return this;
    }

    /**
     * 造一段假数据，这个数据一般去数据库里面取的
     * @param map map
     * @return list
     */
    private List<Map<String, Object>> initData(Map<String, String> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        int days = DateUtil.calcDiffDay(map.get("start"), map.get("end"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(map.get("start")));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期型字符串格式错误");
        }
        for (int i = 0; i <= days; i++) {
            Map<String, Object> map1 = new HashMap<>();
            Map<String, Object> map2 = new HashMap<>();
            calendar.add(Calendar.DAY_OF_MONTH, i == 0 ? 0 : 1);

            map1.put("date", format.format(calendar.getTime()));
            map2.put("date", format.format(calendar.getTime()));
            map1.put("type", "e1");
            map2.put("type", "e2");
            map1.put("total", Math.random() * 100);
            map2.put("total", Math.random() * 100);
            list.add(map1);
            list.add(map2);
        }

        return list;
    }
}
