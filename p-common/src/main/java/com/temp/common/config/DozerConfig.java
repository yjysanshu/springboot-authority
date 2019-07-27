package com.temp.common.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author yuanjy
 * @date 2018/12/15
 * 返回一个dozer实例bean，目的是做对象间属性值的赋值操作
 */
@Configuration
public class DozerConfig {
    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(Collections.singletonList("config/dozer-date.xml"));
        return dozerBeanMapper;
    }
}
