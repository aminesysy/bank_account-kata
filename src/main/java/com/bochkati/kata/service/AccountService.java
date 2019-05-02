package com.bochkati.kata.service;

import com.bochkati.kata.builder.AccountTransactionBuilder;
import com.bochkati.kata.domain.Account;
import com.bochkati.kata.domain.Transaction;
import com.bochkati.kata.domain.TransactionType;
import com.bochkati.kata.exception.WrongAmountException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class AccountService {
    public BigDecimal computeBalance(Account account) {
        double debitd = account
                        .getTransactions()
                        .stream()
                        .filter(record -> TransactionType.DEBIT.getSens().equals(record.getTransactionType().getSens()))
                        .mapToDouble(record -> record.getAmount().doubleValue())
                        .sum();

        double creditd = account
                .getTransactions()
                .stream()
                .filter(record -> TransactionType.CREDIT.getSens().equals(record.getTransactionType().getSens()))
                .mapToDouble(record -> record.getAmount().doubleValue())
                .sum();

        return BigDecimal.valueOf(creditd - debitd);
    }

    private BigDecimal executeTransaction(Account account,
                                          BigDecimal amount,
                                          TransactionType transactionType) throws WrongAmountException {
        if (amount.doubleValue() < 0) {
            throw new WrongAmountException();
        }

        Transaction transaction =  new AccountTransactionBuilder()
                                    .createBuilder()
                                    .id(String.valueOf(account.getTransactions().size() + 1))
                                    .amount(amount)
                                    .recordType(transactionType)
                                    .date(new Date())
                                    .build();

        account.getTransactions().add(transaction);

        return computeBalance(account);
    }

    /*-------------------------------------------------------------*/
    /*            DEPOSIT                                          */
    /*-------------------------------------------------------------*/
    public BigDecimal deposit(Account account, BigDecimal amount)  {
        return executeTransaction(account, amount, TransactionType.DEBIT);
    }

    /*-------------------------------------------------------------*/
    /*            WITHDRAW                                         */
    /*-------------------------------------------------------------*/
    public BigDecimal withdraw(Account account, BigDecimal amount) {
        return executeTransaction(account, amount, TransactionType.CREDIT);
    }

    /*-------------------------------------------------------------*/
    /*            STATEMENT                                        */
    /*-------------------------------------------------------------*/
    public String statment(Account account) {
        return account.print(Optional.empty());
    }
}
