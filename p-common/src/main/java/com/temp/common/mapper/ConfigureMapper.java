package com.temp.common.mapper;

import com.temp.common.model.entity.Configure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfigureMapper extends BaseMapper<Configure> {
    List<Configure> queryListByNames(@Param(value = "names") String[] names);
}