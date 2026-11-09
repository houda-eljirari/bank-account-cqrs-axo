package ma.tp.bank.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreditAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final double amount;

    public CreditAccountCommand(String accountId, double amount) {
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