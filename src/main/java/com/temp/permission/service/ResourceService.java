package com.temp.permission.service;

import com.temp.permission.entity.Resource;
import com.temp.permission.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper mapper;

    public List<Resource> getAllByType(Integer type) {
        return mapper.queryAllByType(type);
    }


}
