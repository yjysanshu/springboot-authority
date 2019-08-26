package com.temp.generator.mapper;

import com.temp.generator.models.Table;

import java.util.List;
import java.util.Map;

public interface TableMapper {
    List<Table> select(Map param);
}
