package com.company.jmixbpm.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Random;

public class RandomIndexDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Long numberOfUsers = execution.getVariable("numberOfUsers", Long.class);
        Long randomIndex = new Random().nextLong(numberOfUsers);
        execution.setVariable("randomIndex", randomIndex);

        System.out.println("Service task: " + execution.getCurrentActivityId() + " randomIndex: " + randomIndex);
    }
}