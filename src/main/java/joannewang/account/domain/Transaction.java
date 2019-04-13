package joannewang.account.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "seq", sequenceName = "address_seq")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Version
    private Integer version;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date valueDate;

    private BigDecimal debitAmount;
    private BigDecimal creditAmount;

    private String debitCredit;
    private String transactionNarrative;

    public Transaction( Date valueDate,
                       BigDecimal debitAmount, BigDecimal creditAmount, String debitCredit, String transactionNarrative) {
        Objects.requireNonNull(valueDate, "Value Date must not be null");
        Objects.requireNonNull(debitCredit, "Debit/Credit must not be null");

        this.valueDate = valueDate;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.debitCredit = debitCredit;
        this.transactionNarrative = transactionNarrative;
    }

    protected Transaction() {
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(String debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction transaction = (Transaction) o;

        if (!Objects.equals(valueDate, transaction.valueDate)) return false;
        if (!Objects.equals(debitAmount, transaction.debitAmount)) return false;
        if (!Objects.equals(creditAmount, transaction.creditAmount)) return false;
        if (!Objects.equals(debitCredit, transaction.debitCredit)) return false;

        return Objects.equals(transactionNarrative, transaction.transactionNarrative);
    }

    @Override
    public int hashCode() {
        int result = valueDate != null ? valueDate.hashCode() : 0;
        result = 31 * result + (debitAmount != null ? debitAmount.hashCode() : 0);
        result = 31 * result + (creditAmount != null ? creditAmount.hashCode() : 0);
        result = 31 * result + (debitCredit != null ? debitCredit.hashCode() : 0);
        result = 31 * result + (transactionNarrative != null ? transactionNarrative.hashCode() : 0);

        return result;
    }
}
