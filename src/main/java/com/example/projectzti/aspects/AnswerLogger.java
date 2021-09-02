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

    /**
     * Advice which measures method execution time in milliseconds and logs this measurement
     * @param pjp JoinPoint execution context
     * @return execution result
     * @throws Throwable Propagate profiled method exceptions
     */
    @Around("execution(* com.example.projectzti.database.services.AnswerService.*(..)))")
    public Object profileServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        Object response = pjp.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        logger.info(String.format("AnswerService method execution succeeded in %d ms", duration));
        return response;
    }

    /**
     * Advice logging start of getting all survey's answers
     */
    @Before("execution(* com.example.projectzti.database.services.AnswerService.getSurveyAnswers(..)))")
    public void logGettingAllSurveysAnswers() {
        logger.info("Getting all survey's answer...");
    }

    /**
     * Pointcut on saving new answer
     */
    @Pointcut("execution(* com.example.projectzti.database.services.AnswerService.insertAnswer(..)))")
    public void saveAnswer() {
    }

    /**
     * Advice logging start of saving answer process
     */
    @Before("saveAnswer()")
    public void logSavingAnswer() {
        logger.info("Inserting new answer...");
    }

    /**
     * Advice validating answer to be saved
     * @param answer Answer to be saved
     * @throws Exception Validation error
     */
    @Before("saveAnswer() && args(answer)")
    public void validateSavingAnswer(ClientAnswer answer) throws Exception {
        if(answer.getSurveyId().compareTo(new UUID(0L, 0L)) == 0){
            throw new Exception("Cannot save answer to survey without id");
        }
    }
}
