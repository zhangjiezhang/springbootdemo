package xyz.pascall.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import xyz.pascall.demo.utils.RedisOperator;

import java.util.UUID;

/**
 * 用于操作redis中存储的对象
 * 基础controller类，提供redis客户端操作对象和redis-session静态字段
 */
@RestController
public class BasicController {

	@Autowired
	protected RedisOperator redis;
	
	protected static final String USER_REDIS_SESSION = "user-redis-session";

	protected static final int REDIS_SESSION_TIME = 1800;

	/**
	 * 保存用户对象到redis,返回token令牌
	 * @param user
	 * @return
	 */
	protected String saveUser(Object user){
		String token = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION+":"+token, user, REDIS_SESSION_TIME);
		return token;
	}

	/**
	 * 根据token判断，用户是否存在
	 * @param token
	 * @return
	 */
	protected  boolean userIsEXIST(String token){
		long time = redis.ttl(USER_REDIS_SESSION+":"+token);
		System.err.println(time);
		if(time <= 0){
			return false;
		}
		return true;
	}

	/**
	 * 根据
	 * @param token
	 * @return
	 */
	protected boolean updateTime(String token){
		if(!userIsEXIST(token)){
			return false;
		}
		redis.expire(USER_REDIS_SESSION+":"+token, REDIS_SESSION_TIME);
		return true;
	}

	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	protected Object getUser(String token){
		if(!updateTime(token)){
			return false;
		}
		Object user = redis.get(USER_REDIS_SESSION+":"+token);
		//System.err.println(userStr);
		return user;
	}
}
