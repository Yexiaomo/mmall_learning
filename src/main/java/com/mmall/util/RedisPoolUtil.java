package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {
    public static String set(String key, String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key : {}, value:{}, error", key, value, e);
            RedisPool.returnBorkenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key : {}, value:{}, extime:{} error", key, value, exTime, e);
            RedisPool.returnBorkenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    public static Long expire(String key, int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key : {} error", key, e);
            RedisPool.returnBorkenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key : {}, error", key, e);
            RedisPool.returnBorkenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key : {}, error", key, e);
            RedisPool.returnBorkenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("key1", "val1");
         String key1 = RedisPoolUtil.get("key1");
        RedisPoolUtil.setEx("keyEx", "valEx",60*10);
        RedisPoolUtil.expire("key1",60*20);
        RedisPoolUtil.del("key1");
    }
}