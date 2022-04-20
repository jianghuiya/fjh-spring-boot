package cn.middleware.ratelimiter;

import cn.middleware.ratelimiter.annotation.DoRateLimiter;
import cn.middleware.ratelimiter.valve.IValveService;
import cn.middleware.ratelimiter.valve.impl.RateLimiterValve;
import com.jh.method.MethodUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DoRateLimiterPoint {

    @Autowired
    MethodUnit methodUnit;

    @Pointcut("@annotation(cn.middleware.ratelimiter.annotation.DoRateLimiter)")
    public void aopPoin(){

    }

    @Around("aopPoin() && @annotation(doRateLimiter)")
    public Object doRouter(ProceedingJoinPoint jp, DoRateLimiter doRateLimiter) throws Throwable {
        IValveService iValveService = new RateLimiterValve();
        return iValveService.access(jp,methodUnit.getMethon(jp),doRateLimiter,jp.getArgs());
    }

}
