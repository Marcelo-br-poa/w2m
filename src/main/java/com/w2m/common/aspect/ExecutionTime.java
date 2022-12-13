package com.w2m.common.aspect;

import com.w2m.common.GeneralMessages;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTime {

    @Around("execution(* com.w2m.application.service.*.*(..))")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long timeStart = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timefinish = System.currentTimeMillis();

        long valueTime = timefinish - timeStart;
        log.info(GeneralMessages.LOG_TIEMPO_EJECUCION,  valueTime, joinPoint.getSignature().getName() ,joinPoint.getTarget().getClass());
        return result;
    }
}
