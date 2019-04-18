package com.bochkati.kata.domain;

public enum TransactionType {
    DEBIT("D"), CREDIT("C");

    private String sens ;

    private TransactionType(String sens) {
        this.sens = sens;
    }

    public String getSens() {
        return this.sens;
    }
}
