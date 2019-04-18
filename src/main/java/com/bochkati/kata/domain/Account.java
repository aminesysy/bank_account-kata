package com.bochkati.kata.domain;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

public class Account {

    private static final String NEW_LINE = "\n";
    private static final String DOUBLE_NEW_LINE = "\n\n";
    private static final String TAB = "\t";

    private static final String STATMENT_HEADER =
                    "+-----------+------------+--------+--------------+\n" +
                    "| AccountID |    Date    |  TYPE  |    Amount    |\n" +
                    "+-----------+------------+--------+--------------+\n";

    private static final String STATMENT_FOOTER =
                    "+-----------+------------+--------+--------------+";
    private String                  accountId;
    private String                  customerId;
    private List<Transaction> transactions;

    public Account() {
        transactions = Lists.newArrayList();
    }

    public Account(String accountId, String customerId, List<Transaction> transactions) {
        super();
        this.accountId = accountId;
        this.customerId = customerId;
        this.transactions = transactions;
    }

    public String print(Optional<List<Transaction>> filteredAccountRecord) {
        List<Transaction> localTransactions = this.transactions;
        if(filteredAccountRecord.isPresent()) {
            localTransactions = filteredAccountRecord.get();
        }

        StringBuilder sb = new StringBuilder();
        String bankAccountIdStr = accountId;
        String customerIdStr = customerId;

        sb.append("AccountId:").append(TAB).append(bankAccountIdStr);
        sb.append(NEW_LINE);
        sb.append("CustomerId:").append(TAB).append(customerIdStr);
        sb.append(DOUBLE_NEW_LINE).append(STATMENT_HEADER);

        for(Transaction transaction : localTransactions) {
            transaction.print(sb);
        }

        sb.append(STATMENT_FOOTER);

        return sb.toString();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
