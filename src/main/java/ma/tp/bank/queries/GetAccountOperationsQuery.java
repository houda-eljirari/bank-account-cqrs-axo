package ma.tp.bank.queries;

public class GetAccountOperationsQuery {

    private final String accountId;

    public GetAccountOperationsQuery(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}