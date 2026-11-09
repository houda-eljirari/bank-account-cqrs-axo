package ma.tp.bank.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateAccountStatusCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final String status;

    public UpdateAccountStatusCommand(String accountId, String status) {
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