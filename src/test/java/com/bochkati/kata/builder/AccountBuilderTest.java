package com.bochkati.kata.builder;

import com.bochkati.kata.domain.Account;
import com.bochkati.kata.service.AccountService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountBuilderTest {

    private Account account;
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DOUBLE_NEW_LINE = NEW_LINE + NEW_LINE;
    private static final String SHOULD_PRINT = ""
            + 	"AccountId:	ACC-01" + NEW_LINE
            +	"CustomerId:	BOCHKATI-01" + DOUBLE_NEW_LINE
            + 	"+-----------+------------+--------+--------------+" + NEW_LINE
            +	"| AccountID |    Date    |  TYPE  |    Amount    |" + NEW_LINE
            +	"+-----------+------------+--------+--------------+" + NEW_LINE
            +	"+-----------+------------+--------+--------------+";

    @Before
    public void setUp() {

    account = new AccountBuilder()
                .createBuilder()
                .id("ACC-01")
                .customer("BOCHKATI-01")
                .build();
    }

    @Test
    public void testBuildAccountZeroRecords() {
        AccountService service = new AccountService();
        String statment = service.statment(account);
        assertThat(statment).isEqualTo(SHOULD_PRINT);
    }
}
