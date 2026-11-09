package ma.tp.bank.events;

public class AccountDebitedEvent {

    private final String accountId;
    private final double amount;

    public AccountDebitedEvent(String accountId, double amount) {
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