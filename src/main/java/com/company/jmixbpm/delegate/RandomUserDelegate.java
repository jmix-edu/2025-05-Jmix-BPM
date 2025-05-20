package com.company.jmixbpm.delegate;

import com.company.jmixbpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomUserDelegate implements JavaDelegate {
    private final SystemAuthenticator systemAuthenticator;
    private final DataManager dataManager;

    public RandomUserDelegate(SystemAuthenticator systemAuthenticator, DataManager dataManager) {
        this.systemAuthenticator = systemAuthenticator;
        this.dataManager = dataManager;
    }

    @Override
    public void execute(DelegateExecution execution) {
        List<User> userList;
        Long randomIndex = execution.getVariable("randomIndex", Long.class);

        systemAuthenticator.begin();
        try {
            userList = dataManager.load(User.class).all().list();
        } finally {
            systemAuthenticator.end();
        }

        String usernameUpper = userList.get(Math.toIntExact(randomIndex))
                .getUsername().toUpperCase();
        String message = execution.getVariable("message", String.class);
        execution.setVariable("message", usernameUpper + ", " + message);

        System.out.println("Service task: " + execution.getCurrentActivityId() + " Random username: " + usernameUpper);
    }
}