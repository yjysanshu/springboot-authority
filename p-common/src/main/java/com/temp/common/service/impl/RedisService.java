package com.temp.common.service.impl;

import com.temp.common.service.IRedisService;

import java.util.List;

public class RedisService implements IRedisService {
    @Override
    public boolean set(String key, String value) throws Exception {
        return false;
    }

    @Override
    public String get(String key) throws Exception {
        return null;
    }

    @Override
    public boolean expire(String key, long expire) throws Exception {
        return false;
    }

    @Override
    public <T> boolean setList(String key, List<T> list) throws Exception {
        return false;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) throws Exception {
        return null;
    }

    @Override
    public long lpush(String key, Object obj) throws Exception {
        return 0;
    }

    @Override
    public long rpush(String key, Object obj) throws Exception {
        return 0;
    }

    @Override
    public void hmset(String key, Object obj) throws Exception {

    }

    @Override
    public <T> T hget(String key, Class<T> clz) throws Exception {
        return null;
    }

    @Override
    public void del(String key) throws Exception {

    }

    @Override
    public <T> List<T> hmGetAll(String key, Class<T> clz) throws Exception {
        return null;
    }

    @Override
    public String lpop(String key) throws Exception {
        return null;
    }
}
