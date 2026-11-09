package ma.tp.bank.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DebitAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final double amount;

    public DebitAccountCommand(String accountId, double amount) {
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