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

@Aspect
@Component
public class SurveyLogger {

    Logger logger = LoggerFactory.getLogger(SurveyLogger.class);

    @Around("execution(* com.example.projectzti.database.services.SurveyService.*(..)))")
    public Object profileServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        Object response = pjp.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        logger.info(String.format("SurveyService method execution succeeded in %d ms", duration));
        return response;
    }

    @Before("execution(* com.example.projectzti.database.services.SurveyService.getAllSurveys(..)))")
    public void logGettingAllSurveys(){
        logger.info("Getting all saved surveys...");
    }

    @Before("execution(* com.example.projectzti.database.services.SurveyService.getSurvey(..)))")
    public void logGettingSurvey(){
        logger.info("Getting specific saved survey...");
    }

    @Pointcut("execution(* com.example.projectzti.database.services.SurveyService.getSurvey(..)))")
    public void saveSurvey() {}

    @Before("saveSurvey()")
    public void logInsertingSurvey(){
        logger.info("Saving new survey...");
    }

    @Before("saveSurvey() && args(uuid)")
    public void validateGettingOneSurvey(UUID uuid) throws Exception {
        if(uuid.compareTo(new UUID(0L, 0L)) == 0){
            throw new Exception("Empty UUID is not allowed here!");
        }
    }
}
