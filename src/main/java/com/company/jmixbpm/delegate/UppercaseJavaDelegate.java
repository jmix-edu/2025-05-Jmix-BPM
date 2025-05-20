package com.company.jmixbpm.delegate;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class UppercaseJavaDelegate implements JavaDelegate {

    private Expression messageField;
    private Expression decorationField;

    @Override
    public void execute(DelegateExecution execution) {
        String message = (String) messageField.getValue(execution);
        String decoration = (String) decorationField.getValue(execution);

        String uppercaseMessage = message.toUpperCase();

        execution.setVariable("message", decoration + uppercaseMessage + decoration);

        System.out.println("Service task: " + execution.getCurrentActivityId() + " Changed message: " + uppercaseMessage);
    }
}