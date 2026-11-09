package ma.tp.bank.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AddAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final double initialBalance;

    public AddAccountCommand(String accountId, double initialBalance) {
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