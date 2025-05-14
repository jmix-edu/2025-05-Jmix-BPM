package com.company.jmixbpm.listener;

import com.company.jmixbpm.entity.User;
import io.jmix.core.DataManager;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApproveTaskListener implements TaskListener {
    private static final Logger log = LoggerFactory.getLogger(ApproveTaskListener.class);
    private final DataManager dataManager;

    public ApproveTaskListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getAssignee();
        User assigneeUser = dataManager.load(User.class).query("select u from User u where u.username = :assignee")
                .parameter("assignee", assignee).one();
        delegateTask.setVariable("approver", assigneeUser);
        log.info("Task listener: {}", assignee);
    }
}
