package by.andron.aspect;

import by.andron.cache.Cache;
import by.andron.cache.impl.LRUCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CacheAspect {

    private final Cache<Long, Object> cache = new LRUCache(20);

    @Pointcut("execution(public !void by.andron.service.*.findById(..))")
    public void findByIdMethod() {
    }

    @Pointcut("execution(public !void by.andron.service.*.update(..))")
    public void updateMethod(){
    }

    @Pointcut("execution(public !void by.andron.service.*.save(..))")
    public void saveMethod(){}

    @Pointcut("execution(public !void by.andron.service.*.delete(..))")
    public void deleteMethod(){
    }


    @Around("findByIdMethod()")
    public void cachingFindByIdResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        if(cache.containsKey(id)){
            return;
        }
        Object retVal = joinPoint.proceed();
        cache.put(id, retVal);
    }

    @Around("updateMethod()")
    public void cachingSaveAndUpdateResult(ProceedingJoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        Object object = joinPoint.getArgs()[1];
        if(cache.containsKey(id)){
            cache.put(id, object);
        }
    }

    @Around("deleteMethod()")
    public void cachingDeleteMethod(ProceedingJoinPoint joinPoint){
        Long id = (Long) joinPoint.getArgs()[0];
        if(cache.containsKey(id)){
            cache.delete(id);
        }
    }

}
