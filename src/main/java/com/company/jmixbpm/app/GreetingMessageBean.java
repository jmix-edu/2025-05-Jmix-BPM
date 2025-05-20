package com.company.jmixbpm.app;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component(value = "greetingMessageBean")
public class GreetingMessageBean {

    public LocalTime printMessage(String greeting, String message, DelegateExecution execution) {

        System.out.println("Service task: " + execution.getCurrentActivityId());
        System.out.println(greeting + "! " + message);

        return LocalTime.now();
    }

    public void printTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = time.format(formatter);

        System.out.println("Greeting time is: " + formattedTime);

    }

}