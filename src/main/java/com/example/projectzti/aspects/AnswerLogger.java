package com.example.projectzti.aspects;

import com.example.projectzti.shared.ClientAnswer;
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
 * Aspect logging and profiling answer operations
 */
@Aspect
@Component
public class AnswerLogger {

    Logger logger = LoggerFactory.getLogger(AnswerLogger.class);

    @Around("execution(* com.example.projectzti.database.services.AnswerService.*(..)))")
    public Object profileServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        Object response = pjp.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        logger.info(String.format("AnswerService method execution succeeded in %d ms", duration));
        return response;
    }

    @Before("execution(* com.example.projectzti.database.services.AnswerService.getSurveyAnswers(..)))")
    public void logGettingAllSurveys() {
        logger.info("Getting all survey's answer...");
    }

    @Pointcut("execution(* com.example.projectzti.database.services.AnswerService.insertAnswer(..)))")
    public void saveAnswer() {
    }

    @Before("saveAnswer()")
    public void logSavingAnswer() {
        logger.info("Inserting new answer...");
    }

    @Before("saveAnswer() && args(answer)")
    public void validateSavingAnswer(ClientAnswer answer) throws Exception {
        if(answer.getSurveyId().compareTo(new UUID(0L, 0L)) == 0){
            throw new Exception("Cannot save answer to survey without id");
        }
    }
}
