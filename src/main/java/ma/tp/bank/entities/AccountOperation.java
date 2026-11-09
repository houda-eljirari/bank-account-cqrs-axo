package ma.tp.bank.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_operations")
public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String operationType;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime operationDate;

    public AccountOperation() {
    }

    public AccountOperation(String accountId, String operationType,
                            double amount, LocalDateTime operationDate) {
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
        this.operationDate = operationDate;
    }

    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOperationType() {
        return operationType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }
}

