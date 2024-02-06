package com.w2m.superhero.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.w2m.superhero.annotation.MeasureExecution;

@Component
@Aspect
public class ExecutionTimeAspect {
	private static final Logger logger = LogManager.getLogger(ExecutionTimeAspect.class);

	@Around("@annotation(measureExecution)")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint, MeasureExecution measureExecution)
			throws Throwable {
		long startTime = System.nanoTime();

		Object result = joinPoint.proceed();

		long endTime = System.nanoTime();
		long executionTimeInNanos = endTime - startTime;
		Signature signature = joinPoint.getSignature();

		// Time in milliseconds
		double executionTimeInMilliseconds = executionTimeInNanos / 1e6;
		logger.info("{} execution time: {}ms", signature.toShortString(), executionTimeInMilliseconds);

		return result;
	}
}
