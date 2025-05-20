package com.company.jmixbpm.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class MyDelegate implements JavaDelegate {

    private int counter;

    public MyDelegate() {
        System.out.println("Instance created: " + this);

    }

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("MiDelegate executed: "
        + ", activity ID: " + execution.getCurrentActivityId()
        + ", counter " + counter++);
    }
}
