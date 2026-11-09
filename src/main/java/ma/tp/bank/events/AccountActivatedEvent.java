package ma.tp.bank.events;

public class AccountActivatedEvent {

    private final String accountId;

    public AccountActivatedEvent(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}