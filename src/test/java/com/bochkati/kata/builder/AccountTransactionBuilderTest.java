package com.bochkati.kata.builder;

import static org.assertj.core.api.Assertions.assertThat;
import com.bochkati.kata.domain.Account;
import com.bochkati.kata.domain.Transaction;
import com.bochkati.kata.exception.WrongAmountException;
import com.bochkati.kata.service.AccountService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountTransactionBuilderTest {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DOUBLE_NEW_LINE = NEW_LINE + NEW_LINE;
    private static final Object EMPTY_STATMENT = ""
            + "AccountId:	ACC-01" + NEW_LINE
            + "CustomerId:	BOCHKATI-01" + DOUBLE_NEW_LINE
            + "+-----------+------------+--------+--------------+" + NEW_LINE
            + "| AccountID |    Date    |  TYPE  |    Amount    |" + NEW_LINE
            + "+-----------+------------+--------+--------------+" + NEW_LINE
            + "+-----------+------------+--------+--------------+";

    private String expectedStatement = ""
            + "AccountId:	ACC-01" + NEW_LINE
            + "CustomerId:	BOCHKATI-01" + DOUBLE_NEW_LINE
            + "+-----------+------------+--------+--------------+" + NEW_LINE
            + "| AccountID |    Date    |  TYPE  |    Amount    |" + NEW_LINE
            + "+-----------+------------+--------+--------------+" + NEW_LINE
            + "|      1    | %s | CREDIT | 0001000,0000 |" + NEW_LINE
            + "|      2    | %s |  DEBIT | 0001500,0000 |" + NEW_LINE
            + "+-----------+------------+--------+--------------+";

    private AccountService service;
    private Account account;


    @Before
    public void setUp() {

        service = new AccountService();

        account = new AccountBuilder()
                .createBuilder()
                .id("ACC-01")
                .customer("BOCHKATI-01")
                .build();

        String today = Transaction.DD_MM_YYYY.format(LocalDate.now());
        expectedStatement = String.format(expectedStatement, today, today);
    }

    @Test
    public void shouldPrintSomeStatements() {
        service.withdraw(account, BigDecimal.valueOf(1000.0));
        service.deposit(account, BigDecimal.valueOf(1500.0));
        String statment = service.statment(account);
        assertThat(statment).isEqualTo(expectedStatement);
    }

    @Test
    public void shouldPrintEmptyStatement() {
        String statment = service.statment(account);
        assertThat(statment).isEqualTo(EMPTY_STATMENT);
    }

    @Test
    public void shouldReturn0WhenNewBankAccount() {
        BigDecimal balance = service.computeBalance(account);
        assertThat(balance).isEqualTo(BigDecimal.valueOf(0.0));
    }

    @Test
    public void shouldReturnOldBalncePlus1000WhenNewTransactionCreditOf1000() {
        BigDecimal oldBalnce = service.computeBalance(account);
        service.deposit(account, BigDecimal.valueOf(1000.0));
        assertThat(oldBalnce.subtract(service.computeBalance(account))).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    public void shouldReturnOldBalnceMinus1000WhenNewTransactionWithdrawOf1000() {
        BigDecimal oldBalnce = service.computeBalance(account);
        service.withdraw(account, BigDecimal.valueOf(1000.0));
        assertThat(service.computeBalance(account).subtract(oldBalnce)).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test(expected= WrongAmountException.class)
    public void shouldThrowWrongAmountWhenNegativeAmountSuppliedForDeposit() {
        service.deposit(account, BigDecimal.valueOf(-1000.0));
    }

    @Test(expected= WrongAmountException.class)
    public void shouldThrowWrongAmountWhenNegativeAmountSuppliedForWithdraw() {
        service.withdraw(account, BigDecimal.valueOf(-1000.0));
    }

}
