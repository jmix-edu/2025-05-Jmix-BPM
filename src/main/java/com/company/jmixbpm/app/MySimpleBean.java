package com.company.jmixbpm.app;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component(value = "mySimpleBean")
public class MySimpleBean {

    public void printMessage(DelegateExecution execution) {
        String value = execution.getVariable("message", String.class);
        System.out.println("Message value: " + value);
    }


}