package com.company.jmixbpm.provider;

import com.company.jmixbpm.entity.User;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@UserProvider(value = "randomRobinUserProvider")
public class RandomRobinUserProvider {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private SystemAuthenticator authenticator;
    private static final Logger log = LoggerFactory.getLogger(RandomRobinUserProvider.class);

    /**
     * This method returns username
     */
    public String getUser() {
        authenticator.begin();
        try {
            List<User> accountants = dataManager.load(User.class)
                    .query("SELECT u from User u WHERE u.position = 'Accountant'")
                    .list();
            int randomNumber = new Random().nextInt(accountants.size());
            String selectedUsername = accountants.get(randomNumber).getUsername();
            log.info("Selected accountant: {}", selectedUsername);
            return selectedUsername;
        } finally {
            authenticator.end();
        }
    }
}