package com.chenzl.app.shiro;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.asiainfo.sessionManage.common.CommonTool;
public class RedisManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisManager.class);
	
	private int expire = 1800;
	
	private JedisPool jedisPool = null;
	
	private JedisPool readJedisPool = null;
	
	public RedisManager(){
		
	}
	
	/**
	 * 初始化方法
	 */
	public void initRedis() {
//		LOGGER.debug("RedisManager init...");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxActive")));
		config.setMaxIdle(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxIdle")));
		config.setMaxWait(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxWait")));
		config.setTestOnBorrow(Boolean.parseBoolean(CommonTool.getPropertiesValue("Redis_TestOnBorrow")));
		
		jedisPool = new JedisPool(config, 
				CommonTool.getPropertiesValue("Redis_Master_Host"),
				Integer.parseInt(CommonTool.getPropertiesValue("Redis_Master_Port")),
				0,
				CommonTool.getPropertiesValue("Redis_Password"));
	}
	
	public void initReadRedis() {
//		LOGGER.debug("RedisManager init...");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxActive")));
		config.setMaxIdle(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxIdle")));
		config.setMaxWait(Integer.parseInt(CommonTool.getPropertiesValue("Redis_MaxWait")));
		config.setTestOnBorrow(Boolean.parseBoolean(CommonTool.getPropertiesValue("Redis_TestOnBorrow")));
		
		readJedisPool = new JedisPool(config, 
				CommonTool.getPropertiesValue("Redis_Slave_Host"),
				Integer.parseInt(CommonTool.getPropertiesValue("Redis_Slave_Port")),
				0,
				CommonTool.getPropertiesValue("Redis_Password"));
	}
	
	/**
	 * 销毁方法
	 */
	public void destroyRedis() {
//		LOGGER.debug("RedisManager destroy...");
		if (jedisPool != null) {
			jedisPool.destroy();
		}
	}
	
	public void destroyReadRedis() {
//		LOGGER.debug("RedisManager destroy...");
		if (readJedisPool != null) {
			readJedisPool.destroy();
		}
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		initReadRedis();
		byte[] value = null;
		Jedis jedis = readJedisPool.getResource();
		try{
			value = jedis.get(key);
		}finally{
			readJedisPool.returnResource(jedis);
			destroyReadRedis();
		}
		return value;
	}
	
	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		initReadRedis();
		Set<byte[]> keys = null;
		Jedis jedis = readJedisPool.getResource();
		try{
			keys = jedis.keys(pattern.getBytes());
		}finally{
			readJedisPool.returnResource(jedis);
			destroyReadRedis();
		}
		return keys;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		initRedis();
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(this.expire != 0){
				jedis.expire(key, this.expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
			destroyRedis();
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		initRedis();
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(expire != 0){
				jedis.expire(key, expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
			destroyRedis();
		}
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		initRedis();
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.del(key);
		}finally{
			jedisPool.returnResource(jedis);
			destroyRedis();
		}
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		initRedis();
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.flushDB();
		}finally{
			jedisPool.returnResource(jedis);
			destroyRedis();
		}
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		initReadRedis();
		Long dbSize = 0L;
		Jedis jedis = readJedisPool.getResource();
		try{
			dbSize = jedis.dbSize();
		}finally{
			readJedisPool.returnResource(jedis);
			destroyReadRedis();
		}
		return dbSize;
	}
	
	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
