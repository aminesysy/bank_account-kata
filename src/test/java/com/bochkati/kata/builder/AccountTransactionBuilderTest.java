package com.bochkati.kata.builder;

import static org.assertj.core.api.Assertions.assertThat;
import com.bochkati.kata.domain.Account;
import com.bochkati.kata.domain.Transaction;
import com.bochkati.kata.domain.TransactionType;
import com.bochkati.kata.exception.WrongAmountException;
import com.bochkati.kata.service.AccountService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTransactionBuilderTest {

    private static final Object EMPTY_STATMENT = ""
            + "AccountId:	ACC-01\n"
            + "CustomerId:	BOCHKATI-01\n\n"
            + "+-----------+------------+--------+--------------+\n"
            + "| AccountID |    Date    |  TYPE  |    Amount    |\n"
            + "+-----------+------------+--------+--------------+\n"
            + "+-----------+------------+--------+--------------+";

    private String expectedStatement = ""
            + "AccountId:	ACC-01\n"
            + "CustomerId:	BOCHKATI-01\n\n"
            + "+-----------+------------+--------+--------------+\n"
            + "| AccountID |    Date    |  TYPE  |    Amount    |\n"
            + "+-----------+------------+--------+--------------+\n"
            + "|      1    | %s | CREDIT | 0001000,0000 |\n"
            + "|      2    | %s |  DEBIT | 0001500,0000 |\n"
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

        String today = Transaction.DD_MM_YYYY.format(new Date());
        expectedStatement = String.format(expectedStatement, today, today);
    }

    @Test
    public void shouldPrintSomeStatements() throws WrongAmountException {
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
    public void shouldReturnOldBalncePlus1000WhenNewTransactionCreditOf1000() throws WrongAmountException {
        BigDecimal oldBalnce = service.computeBalance(account);
        service.deposit(account, BigDecimal.valueOf(1000.0));
        assertThat(oldBalnce.subtract(service.computeBalance(account))).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    public void shouldReturnOldBalnceMinus1000WhenNewTransactionWithdrawOf1000() throws WrongAmountException{
        BigDecimal oldBalnce = service.computeBalance(account);
        service.withdraw(account, BigDecimal.valueOf(1000.0));
        assertThat(service.computeBalance(account).subtract(oldBalnce)).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test(expected= WrongAmountException.class)
    public void shouldThrowWrongAmountExcepionWhenNegativeAmountSuppliedForDeposit() throws WrongAmountException {
        service.deposit(account, BigDecimal.valueOf(-1000.0));
    }

    @Test(expected= WrongAmountException.class)
    public void shouldThrowWrongAmountExcepionWhenNegativeAmountSuppliedForWithdraw() throws WrongAmountException {
        service.withdraw(account, BigDecimal.valueOf(-1000.0));
    }

}
