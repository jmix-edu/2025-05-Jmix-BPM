package com.company.jmixbpm.app;

import org.flowable.engine.delegate.BpmnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component(value = "errorService")
public class ErrorService  {
    private static final Logger log = LoggerFactory.getLogger(ErrorService.class);

    private static final int FAIL_PROBABILITY = 65;

    public void probablyError() {
        if (failure()) {
            log.info("Error!");
            throw new BpmnError("error");
        } else {
            log.info("Success!");
        }
    }

    public void technicalError(Boolean isError) {
        if (!isError) {
            return;
        } else {
            throw new RuntimeException("Error!");
        }

    }

    private boolean failure() {
        Random random = new Random();
        return random.nextInt(100) < FAIL_PROBABILITY;
    }

}