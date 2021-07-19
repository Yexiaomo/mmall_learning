package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisShardedPoolUtil {
    public static String set(String key, String value){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error("set key : {}, value:{}, error", key, value, e);
            RedisShardedPool.returnBorkenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key : {}, value:{}, extime:{} error", key, value, exTime, e);
            RedisShardedPool.returnBorkenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }
    public static Long expire(String key, int exTime){
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key : {} error", key, e);
            RedisShardedPool.returnBorkenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String get(String key){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.get(key);
        } catch (Exception e) {
            log.error("get key : {}, error", key, e);
            RedisShardedPool.returnBorkenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }


    public static Long del(String key){
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.del(key);
        } catch (Exception e) {
            log.error("del key : {}, error", key, e);
            RedisShardedPool.returnBorkenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

//    public static void main(String[] args) {
//        ShardedJedis shardedJedis = RedisShardedPool.getJedis();
//        RedisShardedPoolUtil.set("key1", "val1");
//        String key1 = RedisShardedPoolUtil.get("key1");
//        RedisShardedPoolUtil.setEx("keyEx", "valEx",60*10);
//        RedisShardedPoolUtil.expire("key1",60*20);
//        RedisShardedPoolUtil.del("key1");
//    }
}
