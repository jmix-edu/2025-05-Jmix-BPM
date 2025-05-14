package com.company.jmixbpm.listeners;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzStart implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(QuartzStart.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Started process: {}", execution.getProcessDefinitionId());
    }
}