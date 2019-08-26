package com.temp.generator.service;

import com.temp.generator.mapper.TableMapper;
import com.temp.generator.models.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Autowired
    private TableMapper tableMapper;

    public List<Table> getTableInfo(String database, String tableName) {
        Map<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("database", database);
        return tableMapper.select(map);
    }
}
