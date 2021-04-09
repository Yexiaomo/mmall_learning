package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.*;

public class RedisPool {
    private static JedisPool pool;
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


    private static String ip = PropertiesUtil.getProperty("redis.ip");
    private static Integer port = Integer.valueOf(PropertiesUtil.getProperty("redis.port"));


    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        pool = new JedisPool(jedisPoolConfig, ip, port, 2*1000);
    }
    static {
        initPool();
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBorkenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

//    public static void main(String[] args) {
//        Jedis jedis = pool.getResource();
//        jedis.set("a","aa");
//        pool.destroy();
//    }
}
