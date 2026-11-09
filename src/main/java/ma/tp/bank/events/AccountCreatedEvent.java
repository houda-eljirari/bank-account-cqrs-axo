package ma.tp.bank.events;

public class AccountCreatedEvent {

    private final String accountId;
    private final double initialBalance;

    public AccountCreatedEvent(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.initialBalance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}