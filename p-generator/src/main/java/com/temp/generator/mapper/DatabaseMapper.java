package com.temp.generator.mapper;

import com.temp.generator.models.Database;
import com.temp.generator.models.Databases;

import java.util.List;

public interface DatabaseMapper {
    List<Database> select(String databaseName);
    List<Databases> showTables();
}
