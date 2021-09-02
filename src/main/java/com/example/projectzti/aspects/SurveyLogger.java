package com.example.projectzti.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Aspect logging and profiling survey operations
 */
@Aspect
@Component
public class SurveyLogger {

    Logger logger = LoggerFactory.getLogger(SurveyLogger.class);

    /**
     * Advice which measures method execution time in milliseconds and logs this measurement
     * @param pjp JoinPoint execution context
     * @return execution result
     * @throws Throwable Propagate profiled method exceptions
     */
    @Around("execution(* com.example.projectzti.database.services.SurveyService.*(..)))")
    public Object profileServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        Object response = pjp.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        logger.info(String.format("SurveyService method execution succeeded in %d ms", duration));
        return response;
    }

    /**
     * Advice logging start of getting all surveys
     */
    @Before("execution(* com.example.projectzti.database.services.SurveyService.getAllSurveys(..)))")
    public void logGettingAllSurveys(){
        logger.info("Getting all saved surveys...");
    }

    /**
     * Advice logging start of getting survey
     */
    @Before("execution(* com.example.projectzti.database.services.SurveyService.getSurvey(..)))")
    public void logGettingSurvey(){
        logger.info("Getting specific saved survey...");
    }

    /**
     * Pointcut on saving new survey
     */
    @Pointcut("execution(* com.example.projectzti.database.services.SurveyService.getSurvey(..)))")
    public void saveSurvey() {}

    /**
     * Advice logging start of saving survey process
     */
    @Before("saveSurvey()")
    public void logInsertingSurvey(){
        logger.info("Saving new survey...");
    }

    /**
     * Validates saving new survey
     * @param uuid Survey id
     * @throws Exception Validation Error
     */
    @Before("saveSurvey() && args(uuid)")
    public void validateGettingOneSurvey(UUID uuid) throws Exception {
        if(uuid.compareTo(new UUID(0L, 0L)) == 0){
            throw new Exception("Empty UUID is not allowed here!");
        }
    }
}
