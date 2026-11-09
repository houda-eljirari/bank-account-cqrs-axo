package ma.tp.bank.handlers.event;

import ma.tp.bank.entities.Account;
import ma.tp.bank.entities.AccountOperation;
import ma.tp.bank.events.AccountCreatedEvent;
import ma.tp.bank.events.AccountCreditedEvent;
import ma.tp.bank.events.AccountDebitedEvent;
import ma.tp.bank.events.AccountStatusUpdatedEvent;
import ma.tp.bank.repositories.AccountOperationRepository;
import ma.tp.bank.repositories.AccountRepository;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AccountEventHandler {

    private final AccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;

    public AccountEventHandler(
            AccountRepository accountRepository,
            AccountOperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    // Création du compte dans le modèle de lecture
    @EventHandler
    @Transactional
    public void on(AccountCreatedEvent event) {

        Account account = new Account(
                event.getAccountId(),
                event.getInitialBalance(),
                "ACTIVE"
        );

        accountRepository.save(account);
    }

    // Mise à jour après un crédit
    @EventHandler
    @Transactional
    public void on(AccountCreditedEvent event) {

        Account account = accountRepository.findById(event.getAccountId())
                .orElseThrow(() ->
                        new IllegalStateException("Compte introuvable"));

        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);

        AccountOperation operation = new AccountOperation(
                event.getAccountId(),
                "CREDIT",
                event.getAmount(),
                LocalDateTime.now()
        );

        operationRepository.save(operation);
    }

    // Mise à jour après un débit
    @EventHandler
    @Transactional
    public void on(AccountDebitedEvent event) {

        Account account = accountRepository.findById(event.getAccountId())
                .orElseThrow(() ->
                        new IllegalStateException("Compte introuvable"));

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);

        AccountOperation operation = new AccountOperation(
                event.getAccountId(),
                "DEBIT",
                event.getAmount(),
                LocalDateTime.now()
        );

        operationRepository.save(operation);
    }

    // Mise à jour du statut
    @EventHandler
    @Transactional
    public void on(AccountStatusUpdatedEvent event) {

        Account account = accountRepository.findById(event.getAccountId())
                .orElseThrow(() ->
                        new IllegalStateException("Compte introuvable"));

        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
}
