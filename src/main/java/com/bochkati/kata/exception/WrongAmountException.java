package com.bochkati.kata.exception;

public class WrongAmountException extends RuntimeException {
    public WrongAmountException() {
        super();
    }

    public WrongAmountException(String s) {
        super(s);
    }
}
