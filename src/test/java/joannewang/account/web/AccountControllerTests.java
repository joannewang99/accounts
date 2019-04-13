package joannewang.account.web;

import joannewang.account.AccountsApplication;
import joannewang.account.domain.Account;
import joannewang.account.domain.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountsApplication.class, webEnvironment = RANDOM_PORT)
public class AccountControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreateAccount() throws Exception {
        Account account = new Account("585309209", "SGSavings726", Account.Currency.SGD,
                "Savings", new Date(), BigDecimal.valueOf(84327.51));
        account.addTransaction(new Transaction(new Date(), null,
                BigDecimal.valueOf(9540.98), "Credit", null));
        ResponseEntity<Account> response = restTemplate.postForEntity("/account", account, Account.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.hasBody(), is(true));
        Account created = response.getBody();
        assertThat(created.getId(), is(notNullValue()));
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        Account account = new Account("585309209", "SGSavings726", Account.Currency.SGD,
                "Savings", new Date(), BigDecimal.valueOf(84327.51));
        Transaction transaction = new Transaction(new Date(), null,
                BigDecimal.valueOf(9540.98), "Credit", null);
        account.addTransaction(transaction);
        ResponseEntity<Account> response = restTemplate.postForEntity("/account", account, Account.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.hasBody(), is(true));

        Account created = response.getBody();
        assertThat(created.getId(), is(notNullValue()));
        created.setAccountName("Foo");
        created.setAccountNumber("123");
        assertTrue(created.removeTransaction(created.getTransactions().stream().findFirst().get()));

        restTemplate.put("/account", created, Account.class);

        ResponseEntity<Account> response2 = restTemplate.getForEntity("/account/" + created.getId(), Account.class);
        assertThat(response2.getStatusCode(), equalTo(OK));
        assertThat(response2.hasBody(), is(true));
        Account updated = response2.getBody();
        assertThat(updated.getAccountName(), is("Foo"));
        assertThat(updated.getAccountNumber(), is("123"));
        assertThat(updated.getTransactions().size(), is(0));
    }

    @Test
    public void shouldFindAccountWithTransactionsById() throws Exception {
        Account account = new Account("585309209", "SGSavings726", Account.Currency.SGD,
                "Savings", new Date(), BigDecimal.valueOf(84327.51));
        Transaction transaction = new Transaction(new Date(), null,
                BigDecimal.valueOf(9540.98), "Credit", null);
        Transaction transaction2 = new Transaction(new Date(), null,
                BigDecimal.valueOf(7497.82), "Credit", null);

        account.addTransaction(transaction);
        account.addTransaction(transaction2);
        ResponseEntity<Account> response = restTemplate.postForEntity("/account", account, Account.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.hasBody(), is(true));

        Account created = response.getBody();
        ResponseEntity<Account> response2 = restTemplate.getForEntity("/account/" + created.getId(), Account.class);
        assertThat(response2.getStatusCode(), equalTo(OK));
        assertThat(response2.hasBody(), is(true));
        Account account1 = response2.getBody();
        assertThat(account1.getTransactions().size(), is(2));
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        Account account = new Account("585309209", "SGSavings726", Account.Currency.SGD,
                "Savings", new Date(), BigDecimal.valueOf(84327.51));
        Transaction transaction = new Transaction(new Date(), null,
                BigDecimal.valueOf(9540.98), "Credit", null);
        account.addTransaction(transaction);
        ResponseEntity<Account> response = restTemplate.postForEntity("/account", account, Account.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.hasBody(), is(true));
        Account created = response.getBody();
        assertThat(created.getId(), is(notNullValue()));

        restTemplate.delete("/account/" + created.getId());
        ResponseEntity<Account> response2 = restTemplate.getForEntity("/account/" + created.getId(), Account.class);
        assertThat(response2.hasBody(), is(false));
    }

    @Test
    public void shouldListAllAccounts() throws Exception {
        Account account1 = new Account("585309209", "SGSavings726", Account.Currency.SGD,
                "Savings", new Date(), BigDecimal.valueOf(84327.51));
        Transaction transaction = new Transaction(new Date(), null,
                BigDecimal.valueOf(9540.98), "Credit", null);
        account1.addTransaction(transaction);
        ResponseEntity<Account> response = restTemplate.postForEntity("/account", account1, Account.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.hasBody(), is(true));

        ResponseEntity<List<Account>> response2 = restTemplate.exchange("/account",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {
                });
        assertThat(response2.getStatusCode(), equalTo(OK));
        assertThat(response2.hasBody(), is(true));
        List<Account> accounts = response2.getBody();
        assertFalse(accounts.isEmpty());
    }
}
