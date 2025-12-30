package com.CloudKeeper.CloudBalanceBackend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.CloudKeeper.CloudBalanceBackend.controller.UserLoginController.*.*(..))")
    public void loginAuthMethod() {
    }

    @Pointcut("execution(* com.CloudKeeper.CloudBalanceBackend.service.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethods() {
    }

    //    service advice
    @Around("serviceMethods()")
    public Object AroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method is started executing : {}", pjp.getSignature());
        Object obj = pjp.proceed();
        log.info("Method is ended executing : {}", pjp.getSignature());
        return obj;
    }

    //    controller advice
    @Before("controllerMethods()")
    public void BeforeController(JoinPoint jp) {
        log.info("Targeted Controller endpoint : {}", jp.getSignature());
    }
}