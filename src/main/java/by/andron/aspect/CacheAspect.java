package by.andron.aspect;

import by.andron.aspect.annotation.Cacheable;
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
    public void updateMethod() {
    }

    @Pointcut("execution(public !void by.andron.service.*.save(..))")
    public void saveMethod() {
    }

    @Pointcut("execution(public !void by.andron.service.*.delete(..))")
    public void deleteMethod() {
    }

    @Around("findByIdMethod()")
    public Object cachingFindByIdResult(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean hasAnnotation = joinPoint.getTarget().getClass().isAnnotationPresent(Cacheable.class);

        if (hasAnnotation) {
            Long id = (Long) joinPoint.getArgs()[0];
            if (cache.containsKey(id)) {
                return cache.get(id);
            } else {
                Object retVal = joinPoint.proceed();
                cache.put(id, retVal);
                return retVal;
            }
        }
        return joinPoint.proceed();
    }

    @Around("updateMethod()")
    public Object cachingUpdateResult(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean hasAnnotation = joinPoint.getTarget().getClass().isAnnotationPresent(Cacheable.class);

        if (hasAnnotation) {
            Long id = (Long) joinPoint.getArgs()[0];
            Object object = joinPoint.proceed();
            if (cache.containsKey(id)) {
                cache.put(id, object);
                return object;
            }
        }
        return joinPoint.proceed();
    }

    @Around("deleteMethod()")
    public void cachingDeleteMethod(ProceedingJoinPoint joinPoint) {
        boolean hasAnnotation = joinPoint.getTarget().getClass().isAnnotationPresent(Cacheable.class);

        if (hasAnnotation) {
            Long id = (Long) joinPoint.getArgs()[0];
            if (cache.containsKey(id)) {
                cache.delete(id);
            }
        }
    }

}