package by.andron.aspect;

import by.andron.cache.TwoQCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LRUCacheAspect {

    private final TwoQCache cache;

    @Pointcut("@annotation(by.andron.aspect.annotation.Cacheable)")
    public void cacheableMethods() {}

    @Around("cacheableMethods()")
    public void cacheResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        Object retVal = joinPoint.proceed();
        cache.put(id, retVal);
    }

}
