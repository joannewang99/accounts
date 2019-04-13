package joannewang.account.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@SequenceGenerator(name = "seq", sequenceName = "customer_seq")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("balance_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date balanceDate;

    @JsonProperty("opening_available_balance")
    private BigDecimal openingAvailableBalance;

    public enum Currency {AUD, SGD}

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private final Set<Transaction> transactions = new HashSet<>();

    public Account(String accountNumber, String accountName, Currency currency,
                   String accountType, Date balanceDate, BigDecimal openingAvailableBalance) {
        Objects.requireNonNull(accountNumber, "Account Number must not be null");
        Objects.requireNonNull(accountName, "Account Name must not be null");
        Objects.requireNonNull(currency, "Currency must not be null");
        Objects.requireNonNull(accountType, "Account Type must not be null");
        Objects.requireNonNull(balanceDate, "Balance Date must not be null");
        Objects.requireNonNull(openingAvailableBalance, "Opening Available Balance must not be null");

        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.currency = currency;
        this.accountType = accountType;
        this.balanceDate = balanceDate;
        this.openingAvailableBalance = openingAvailableBalance;
    }

    protected Account() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Set<Transaction> getTransactions() {
        return Collections.unmodifiableSet(transactions);
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public BigDecimal getOpeningAvailableBalance() {
        return openingAvailableBalance;
    }

    public void setOpeningAvailableBalance(BigDecimal openingAvailableBalance) {
        this.openingAvailableBalance = openingAvailableBalance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction must not be null");
        return transactions.add(transaction);
    }


    public boolean removeTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction must not be null");
        return transactions.remove(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        if (!Objects.equals(accountNumber, account.accountNumber)) return false;
        if (!Objects.equals(accountName, account.accountName)) return false;
        if (!Objects.equals(accountType, account.accountType)) return false;
        if (!Objects.equals(balanceDate, account.balanceDate)) return false;
        if (!Objects.equals(openingAvailableBalance, account.openingAvailableBalance)) return false;

        return currency == account.currency;
    }

    @Override
    public int hashCode() {
        int result = accountNumber != null ? accountNumber.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (balanceDate != null ? balanceDate.hashCode() : 0);
        result = 31 * result + (openingAvailableBalance != null ? openingAvailableBalance.hashCode() : 0);
        return result;
    }
}
