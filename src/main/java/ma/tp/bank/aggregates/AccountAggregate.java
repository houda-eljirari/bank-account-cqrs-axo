
package ma.tp.bank.aggregates;

import ma.tp.bank.commands.AddAccountCommand;
import ma.tp.bank.commands.CreditAccountCommand;
import ma.tp.bank.commands.DebitAccountCommand;
import ma.tp.bank.commands.UpdateAccountStatusCommand;

import ma.tp.bank.events.AccountCreatedEvent;
import ma.tp.bank.events.AccountCreditedEvent;
import ma.tp.bank.events.AccountDebitedEvent;
import ma.tp.bank.events.AccountStatusUpdatedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private double balance;

    private String status;

    // Constructeur obligatoire pour Axon
    public AccountAggregate() {
    }

    // Création d'un compte
    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {

        if (command.getInitialBalance() < 0) {
            throw new IllegalArgumentException(
                    "Le solde initial ne peut pas être négatif"
            );
        }

        apply(new AccountCreatedEvent(
                command.getAccountId(),
                command.getInitialBalance()
        ));
    }

    // Créditer un compte
    @CommandHandler
    public void handle(CreditAccountCommand command) {

        if (!"ACTIVE".equals(status)) {
            throw new IllegalStateException(
                    "Impossible de créditer un compte non actif"
            );
        }

        if (command.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Le montant du crédit doit être positif"
            );
        }

        apply(new AccountCreditedEvent(
                accountId,
                command.getAmount()
        ));
    }

    // Débiter un compte
    @CommandHandler
    public void handle(DebitAccountCommand command) {

        if (!"ACTIVE".equals(status)) {
            throw new IllegalStateException(
                    "Impossible de débiter un compte non actif"
            );
        }

        if (command.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Le montant du débit doit être positif"
            );
        }

        if (balance < command.getAmount()) {
            throw new IllegalStateException(
                    "Solde insuffisant"
            );
        }

        apply(new AccountDebitedEvent(
                accountId,
                command.getAmount()
        ));
    }

    // Modifier le statut du compte
    @CommandHandler
    public void handle(UpdateAccountStatusCommand command) {

        if (command.getStatus() == null ||
                command.getStatus().isBlank()) {
            throw new IllegalArgumentException(
                    "Le statut ne peut pas être vide"
            );
        }

        apply(new AccountStatusUpdatedEvent(
                accountId,
                command.getStatus().toUpperCase()
        ));
    }

    // Reconstruction de l'état après création
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = "ACTIVE";
    }

    // Reconstruction après crédit
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    // Reconstruction après débit
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }

    // Reconstruction après changement de statut
    @EventSourcingHandler
    public void on(AccountStatusUpdatedEvent event) {
        this.status = event.getStatus();
    }
}
