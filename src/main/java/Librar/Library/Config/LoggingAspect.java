package Librar.Library.Config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* Librar.Library.Servess..*(..)) && @within(org.springframework.stereotype.Service)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @After("execution(* Librar.Library.Servess..*(..)) && @within(org.springframework.stereotype.Service)")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature());
    }

    @Around("execution(* Librar.Library.Servess..*(..)) && @within(org.springframework.stereotype.Service)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("KPI: Method {} executed in {} ms. Performance Metric: {} ms",
                    joinPoint.getSignature(),
                    timeTaken,
                    timeTaken);
            return result;
        } catch (Exception e) {
            logger.error("KPI: Exception in method {} with message: {}",
                    joinPoint.getSignature(),
                    e.getMessage());
            throw e;
        }
    }
}
