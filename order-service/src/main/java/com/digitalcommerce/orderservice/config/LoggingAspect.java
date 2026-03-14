package com.digitalcommerce.orderservice.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.digitalcommerce.orderservice.service.*.*(..))")
    public Object beforeServiceMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        System.out.println("Time taken for service call:"
                + proceedingJoinPoint.getSignature().getName() + " : " + (System.currentTimeMillis() - startTime) + " ms");
        return result;
    }

    @Around("execution(* com.digitalcommerce.orderservice.jpa.repository.*.*(..))")
    public Object manageDatabaseCallExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        System.out.println("Time taken for DB call:"
                + proceedingJoinPoint.getSignature().getName() + " : " + (System.currentTimeMillis() - startTime) + " ms");
        return result;
    }

    /*@AfterThrowing
    public void afterMethodException(JoinPoint joinPoint) {
        // Logic to log exceptions thrown by methods (e.g., exception type, message)
    }

    @AfterReturning
    public void afterMethodReturn(JoinPoint joinPoint) {
        // Logic to log successful method returns (e.g., return value)
    }

    @After("")
    public void afterMethodExecution(JoinPoint joinPoint) {
        // Logic to log method execution results (e.g., return value, execution time)
    }*/
}
