package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheHashAspect {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.select*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        StringBuilder stb = new StringBuilder();
        //获取类的全路径
        String classname = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodname = joinPoint.getSignature().getName();
        stb.append(classname).append(methodname);
        //获取实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            stb.append(arg);
        }
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object proceed = null;
        if(hashOperations.hasKey(classname, stb.toString())){
            //如果有这个key
            proceed = hashOperations.get(classname, stb.toString());
        }else{
            //  没有这个key
            try {
                proceed = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(classname, stb.toString(), proceed);
        }
        return proceed; // 这个数据会到达controller
    }

    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
    }
}
