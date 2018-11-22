package com.temp.generator.service;

import com.temp.generator.consts.CommonConst;
import com.temp.generator.mapper.DatabaseMapper;
import com.temp.generator.models.Database;
import com.temp.generator.models.Databases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private DatabaseMapper databaseMapper;

    /**
     * 获取数据库中所有的表名
     * @param databaseName 数据库名
     * @return list
     */
    public List<Database> getDatabaseInfo(String databaseName) {
        return databaseMapper.select(databaseName);
    }

    /**
     * 获取所有非系统数据库库名
     * @return list
     */
    public List<Databases> getDatabases() {
        List<Databases> list = databaseMapper.showTables();
        List<Databases> listRemove = new ArrayList<>();
        for (Databases databases : list) {
            if (Arrays.asList(CommonConst.SYSTEM_DATABASE).contains(databases.getDatabase())) {
                listRemove.add(databases);
            }
        }
        for (Databases databases : listRemove) {
            list.remove(databases);
        }
        return list;
    }
}
