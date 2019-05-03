package com.bochkati.kata.builder;

import com.bochkati.kata.domain.Transaction;
import com.bochkati.kata.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountTransactionBuilder {

    private Transaction transaction;

    public AccountTransactionBuilder() {
        transaction = new Transaction();
    }

    public AccountTransactionBuilder createBuilder() {
        return this;
    }

    public AccountTransactionBuilder id(String accountId) {
        transaction.setAccountTransactionId(accountId);
        return this;
    }

    public AccountTransactionBuilder amount(BigDecimal amount) {
        transaction.setAmount(amount);
        return this;
    }

    public AccountTransactionBuilder recordType(TransactionType transactionType) {
        transaction.setTransactionType(transactionType);
        return this;
    }

    public AccountTransactionBuilder date(LocalDate date) {
        transaction.setDate(date);
        return this;
    }

    public Transaction build() {
        return transaction;
    }
}
