package com.oasys.conf;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
  
	// execution(public * EmployeeManager.*(..))
	@Around("execution(public * com.oasys..*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Long startTime = System.currentTimeMillis();
		Object className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		log.info("Request" + "," + className + "," + methodName + "," + "("
				+ Arrays.asList(joinPoint.getArgs()).toString() + ")");
		Object response = joinPoint.proceed();
		Long endTime = System.currentTimeMillis() - startTime;
		log.info("Response" + "," + className + "," + methodName + "," + "("
				+ Arrays.asList(joinPoint.getArgs()).toString() + ")" + "," + response + "," + endTime);
		return response;
	}

	@AfterThrowing(pointcut = "execution(public * com.oasys..*.*(..))", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinpoint, Exception ex) {
		Object className = joinpoint.getTarget().getClass().getName();
		String methodName = joinpoint.getSignature().getName();
		log.error("Error" + "," + className + "," + methodName + "," + "("
				+ Arrays.asList(joinpoint.getArgs()).toString() + ")" + "," + ex.getMessage());

	}
}