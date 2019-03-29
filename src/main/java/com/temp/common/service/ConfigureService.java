package com.temp.common.service;

import com.alibaba.fastjson.JSONObject;
import com.temp.admin.dto.ConfigureDTO;
import com.temp.admin.dto.list.ConfigureListDTO;
import com.temp.common.mapper.ConfigureMapper;
import com.temp.common.model.entity.Configure;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigureService {

    @Autowired
    private ConfigureMapper mapper;

    @Autowired
    private Mapper mapperTrans;

    public List<ConfigureListDTO> getPageList(ConfigureDTO container) {
        Configure configureSearch = mapperTrans.map(container, Configure.class);
        Map<String, Object> map = new HashMap<>();
        map.put("configure", configureSearch);
        map.put("page", container.getCurrentPage());
        map.put("size", container.getLimit());
        List<Configure> configureList = mapper.queryPageList(map);
        List<ConfigureListDTO> list = new ArrayList<>();
        for (Configure configure : configureList) {
            ConfigureListDTO dto = mapperTrans.map(configure, ConfigureListDTO.class);
            list.add(dto);
        }
        return list;
    }

    public Integer getTotal(ConfigureDTO container) {
        Configure configureSearch = mapperTrans.map(container, Configure.class);
        return mapper.queryCount(configureSearch);
    }

    /**
     * 根据配置名称获取所有的配置信息
     * @param names
     * @return
     */
    public Map getValueByNames(String[] names) {
        List<Configure> configureList = mapper.queryListByNames(names);
        Map<String, Object> map = new HashMap<>();
        for (Configure configure : configureList) {
            map.put(configure.getName(), JSONObject.parse(configure.getValue()));
        }
        return map;
    }

    public Integer save(ConfigureDTO container) {
        Configure configure;
        if (container.getId() != null) {
            configure = mapper.queryOne(container.getId());
            if (configure == null) {
               configure = new Configure();
            }
        } else {
            configure = new Configure();
        }

        configure.setName(container.getName());
        configure.setValue(container.getValue());
        configure.setDescription(container.getDescription());
        if (container.getId() != null) {
            return mapper.update(configure);
        } else {
            return mapper.add(configure);
        }
    }

    public Integer delete(Integer id) {
        return mapper.delete(id);
    }
}