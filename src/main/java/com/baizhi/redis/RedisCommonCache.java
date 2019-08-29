package com.baizhi.redis;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration
@Aspect
public class RedisCommonCache {
    @Autowired
    private Jedis jedis;

    //环绕通知切入方法
    @Around("execution(* com.baizhi.service.*.*(..))")
    public Object aroundAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取当前的查询方法
        //判断当前方法上是否有自定义注解
        //如果有自定义注解 先去缓存中取数据
        //如果缓存中有这个方法  直接返回
        //如果没有  查询数据库  并且缓存一份
        //如果没有自定义注解  直接放行
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //方法名
        Method method = signature.getMethod();
        String methodName = method.getName();
        //类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        //拼接keyvalue
        builder.append(methodName).append(":");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            builder.append(arg);
            if (i == args.length - 1) break;
            builder.append(",");
        }
        String keyValue = builder.toString();
        Object result = null;
        //判断切入的方法是否含有注解 rediscache
        if (method.isAnnotationPresent(RedisCache.class)) {
            //判断是否包含该 key keyValue
            System.out.println(jedis);
            System.out.println(className);
            System.out.println(keyValue);
            Boolean b = jedis.hexists(className, keyValue);
            System.out.println(b);
            if (b) {
                String hget = jedis.hget(className, keyValue);
                result = JSONObject.parse(hget);
            } else {
                //不包含 放行 存储
                result = proceedingJoinPoint.proceed();
                jedis.hset(className, keyValue, JSONObject.toJSONString(result));
            }
        } else {
            //放行
            result = proceedingJoinPoint.proceed();
            System.out.println("放行");
        }
        jedis.close();
        return result;
    }

    //后置通知  当增删改 操作 时 清空缓存
    // 表达式： 切入select*外的方法
    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.select*(..))")
    public void afterAop(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        jedis.del(className);
        jedis.close();
    }
}
