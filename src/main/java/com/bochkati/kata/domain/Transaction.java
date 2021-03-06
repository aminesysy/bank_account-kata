package com.bochkati.kata.domain;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Transaction {

    /*----------------------------------------------------------------------------------------*/
    /*            DEFINES                                                                     */
    /*----------------------------------------------------------------------------------------*/
    public static final DateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
    private static final NumberFormat DDDD_DDDD = new DecimalFormat("0000000.0000");
    private static final String NA = "NA";
    private static final String ROW = "| %s    | %s | %s | %s |" + System.lineSeparator();
    /*----------------------------------------------------------------------------------------*/
    private LocalDate           date;
    private String            accountTransactionId;
    private BigDecimal        amount;
    private TransactionType   transactionType;

    public Transaction() {
        super();
    }

    public Transaction(LocalDate date, String accountTransactionId, BigDecimal amount, TransactionType transactionType) {
        this.date = date;
        this.accountTransactionId = accountTransactionId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    /*-------------------------------------------------------------*/
    /*            PRINT Transaction                                     */
    /*-------------------------------------------------------------*/
    void print(StringBuilder sb) {
        String accountTransactionIdStr = accountTransactionId == null ? NA : accountTransactionId;
        String dateStr = date == null ? NA : DD_MM_YYYY.format(date);
        String typeStr = transactionType == null ? NA : transactionType.toString();
        String amountStr = amount == null ? NA : DDDD_DDDD.format(amount);

        sb.append(String.format(ROW,
                StringUtils.leftPad(accountTransactionIdStr, 6, ' '),
                dateStr,
                StringUtils.leftPad(typeStr, 6, ' '),
                amountStr));
    }

    public LocalDate getDate() {
        return  this.date ;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountTransactionId() {
        return accountTransactionId;
    }

    public void setAccountTransactionId(String accountTransactionId) {
        this.accountTransactionId = accountTransactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public static class Builder {

        private Transaction transaction;

        public Builder() {
            transaction = new Transaction();
        }

        public Builder createBuilder() {
            return this;
        }

        public Builder id(String accountTransactionId) {
            transaction.accountTransactionId = accountTransactionId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            transaction.amount = amount;
            return this;
        }

        public Builder recordType(TransactionType transactionType) {
            transaction.transactionType = transactionType;
            return this;
        }

        public Builder date(LocalDate date) {
            transaction.date = date;
            return this;
        }

        public Transaction build() {
            return transaction;
        }
    }
}
