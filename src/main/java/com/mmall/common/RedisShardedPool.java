package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

public class RedisShardedPool {
    private static ShardedJedisPool pool;
    //最大连接数
    private static Integer maxTotal = Integer.valueOf(PropertiesUtil.getProperty("", "20"));
    //在JedisPool中最大的idle状态（空闲的）的jedis实例的个数
    private static Integer maxIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.max.idle", "10"));
    //在JedisPool中最小的idle状态（空闲的）的jedis实例的个数
    private static Integer minIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.min.idle", "2"));
    //在borrow一个jedis实例的时候，是否要验证操作，如果赋值true，则得到的jedis实例时可用的
    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    //在return一个jedis实例的时候，是否要验证操作，如果赋值true，则放回jedisPool的jedis实例时可用的
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.return", "true"));


    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.valueOf(PropertiesUtil.getProperty("redis1.port"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.valueOf(PropertiesUtil.getProperty("redis2.port"));

    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000 * 2);

        ArrayList<JedisShardInfo> shardInfoArrayList = new ArrayList<>(2);
        shardInfoArrayList.add(info1);
        shardInfoArrayList.add(info2);

        pool = new ShardedJedisPool(jedisPoolConfig,shardInfoArrayList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }
    static {
        initPool();
    }
    public static ShardedJedis getJedis(){
        return pool.getResource();
    }
    public static void returnResource(ShardedJedis shardedJedis){
        pool.returnResource(shardedJedis);
    }

    public static void returnBorkenResource(ShardedJedis shardedJedis){
        pool.returnBrokenResource(shardedJedis);
    }

//    public static void main(String[] args) {
//        ShardedJedis jedis = pool.getResource();
//        for(int i = 0; i < 10; i++){
//            jedis.set("key"+i,"value" + i);
//        }
//        returnResource(jedis);
//        System.out.println("program is end!");
//    }
}
