package ma.tp.bank.events;

public class AccountStatusUpdatedEvent {

    private final String accountId;
    private final String status;

    public AccountStatusUpdatedEvent(String accountId, String status) {
        this.accountId = accountId;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getStatus() {
        return status;
    }
}
