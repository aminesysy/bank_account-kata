package com.bochkati.kata.exception;

public class WrongAmountException extends Exception {
    public WrongAmountException() {
        super();
    }

    public WrongAmountException(String s) {
        super(s);
    }
}
