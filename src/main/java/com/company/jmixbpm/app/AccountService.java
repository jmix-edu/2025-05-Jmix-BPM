package com.company.jmixbpm.app;

import com.company.jmixbpm.entity.Account;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.Thread.sleep;

@Component
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private static final int MAX_DELAY = 2000;

    private static final int FAIL_PROBABILITY = 35;

    private final DataManager dataManager;
    private final SystemAuthenticator authenticator;

    public AccountService(DataManager dataManager, SystemAuthenticator systemAuthenticator) {
        this.dataManager = dataManager;
        this.authenticator = systemAuthenticator;
    }

    public boolean debit(String owner, Long amountToDebit) {
        delay();
        if (failure()) return false;

        authenticator.begin();
        try {
            Account account = dataManager.load(Account.class)
                    .query("select a from Account a where a.owner = :owner")
                    .parameter("owner", owner)
                    .one();
            long newAmount = account.getAmount() - amountToDebit;
            if (newAmount < 0L) {
                log.info("ERROR: Can not debit account {} by {}", owner, amountToDebit);
                return false;
            } else {
                account.setAmount(newAmount);
                dataManager.save(account);
                log.info("SUCCESS: Debited account {} by {}", owner, amountToDebit);
                return true;
            }
        } finally {
            authenticator.end();
        }
    }

    public boolean credit(String owner, Long amountToCredit) {
        delay();
        if (failure()) return false;

        authenticator.begin();
        try {
            Account account = dataManager.load(Account.class)
                    .query("select a from Account a where a.owner = :owner")
                    .parameter("owner", owner)
                    .one();
            long newAmount = account.getAmount() + amountToCredit;

            account.setAmount(newAmount);
            dataManager.save(account);
            log.info("SUCCESS: Credited account {} by {}", owner, amountToCredit);
            return true;
        } finally {
            authenticator.end();
        }
    }

    private void delay() {
        Random random = new Random();
        int delay = random.nextInt(MAX_DELAY);
        try {
            sleep(delay);
        } catch (InterruptedException ignored) {

        }
    }

    private boolean failure() {
        Random random = new Random();
        return random.nextInt(100) < FAIL_PROBABILITY;
    }
}