package com.temp.generator.mapper;

import com.temp.generator.models.Table;

import java.util.List;

public interface TableMapper {
    List<Table> select(String tableName);
}
