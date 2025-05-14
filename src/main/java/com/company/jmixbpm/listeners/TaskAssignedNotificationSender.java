package com.company.jmixbpm.listeners;

import com.company.jmixbpm.entity.User;
import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import io.jmix.core.DataManager;
import io.jmix.notifications.NotificationManager;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignedNotificationSender {

    @Autowired
    private DataManager dataManager;
    @Autowired
    protected NotificationManager notificationManager;

    @EventListener
    public void onTaskAssigned(UserTaskAssignedEvent event) {
        User user = dataManager.load(User.class)
                .query("select u from User u where u.username = :username")
                .parameter("username", event.getUsername())
                .one();
        Task task = event.getTask();  //this is flowable task!

        notificationManager.createNotification()
                .withSubject("New task")
                .withRecipients(user)
                .toChannelsByNames("in-app")
                .withPlainTextContentType()
                .withTypeName("task")
                .withBody("A new task  [" + task.getName() + "] is assigned to you")
                .send();
    }
}
