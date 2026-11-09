package ma.tp.bank.events;

public class AccountCreditedEvent {

    private final String accountId;
    private final double amount;

    public AccountCreditedEvent(String accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }
}