package com.bochkati.kata.builder;

import com.bochkati.kata.domain.Account;
import com.bochkati.kata.domain.Transaction;


import java.util.List;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder createBuilder() {
        return this;
    }

    public AccountBuilder id(String accountId) {
        account.setAccountId(accountId);
        return this;
    }

    public AccountBuilder customer(String customerId) {
        account.setCustomerId(customerId);
        return this;
    }

    public AccountBuilder transactions(List<Transaction> transactions) {
        account.setTransactions(transactions);
        return this;
    }

    public AccountBuilder transaction(Transaction transaction) {
        account.getTransactions().add(transaction);
        return this;
    }

    public Account build() {
        return account;
    }
}
