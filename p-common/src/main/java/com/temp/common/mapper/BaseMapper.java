package com.temp.common.mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper基类
 * @param <T>
 */
public interface BaseMapper<T> {

    /**
     * 根据条件查找所有的记录
     * @param record
     * @return list
     */
    List<T> queryList(T record);

    /**
     * 根据条件查找一个页面的记录
     * @param map
     * @return list
     */
    List<T> queryPageList(Map map);

    /**
     * 根据条件查找总数
     * @param record
     * @return int
     */
    int queryCount(T record);

    /**
     * 根据ID查找一条记录
     * @param id
     * @return T
     */
    T queryOne(Integer id);

    /**
     * 根据ID删除记录
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 添加一条记录
     * @param record
     * @return int
     */
    int add(T record);

    /**
     * 修改一条记录
     * @param record
     * @return int
     */
    int update(T record);
}
