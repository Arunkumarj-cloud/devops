package com.oasys.log;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Aspect
@Component
public class Log {
	 private static final Logger logger = (Logger) LoggerFactory.getLogger(Log.class);

	    @Before("execution(*  com.oasys.uppcl_user__master_management.controller..*(..))")
	    public void logBefore(JoinPoint joinPoint) {
	        logger.info("Before " + joinPoint.getSignature().getName() + "method started");
	        System.out.println("--------method started--------");
	    }

	    @After("execution(*  com.oasys.uppcl_user__master_management.controller..*(..))")
	    public void logAfter(JoinPoint joinPoint) {
	        logger.info("After " + joinPoint.getSignature().getName() +"method ended");
	        System.out.println("--------method ended--------");
	    }
	    @Around("execution(*  com.oasys.uppcl_user__master_management.controller..*(..))")
		public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
		{
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
			logger.info(proceedingJoinPoint.getSignature().getName());
			//Get intercepted method details
			String className = methodSignature.getDeclaringType().getSimpleName();
			String methodName = methodSignature.getName();
			logger.info(className+"--"+methodName);
			final StopWatch stopWatch = new StopWatch();

			//Measure method execution time
			stopWatch.start();
			Object result = proceedingJoinPoint.proceed();
			stopWatch.stop();

			//Log method execution time
			logger.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTime() + " "+"milliseconds");

			return result;
		}
	    
//	    @Around("execution(*  com.oasys.uppcl_user__master_management.controller..*(..))")
//	    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//	        long startTime = System.currentTimeMillis();
//
//	        Object result = joinPoint.proceed();
//
//	        long endTime = System.currentTimeMillis();
//	        long executionTime = endTime - startTime;
//
//	        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "millisecond");
//
//	        return result;
//	    }
	    @AfterReturning("execution(* com.oasys.uppcl_user__master_management.controller..*(..))")
	    public void logMethodEnd(JoinPoint joinPoint) {
	        String methodName = joinPoint.getSignature().getName();
	        System.out.println("Finished method: " + methodName);
	    }
	    
	    @AfterThrowing(pointcut = "execution(* com.oasys.uppcl_user__master_management.controller..*(..))", throwing = "e")
	    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
	    	logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
	            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
	    }

	}



