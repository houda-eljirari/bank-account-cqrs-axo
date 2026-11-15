
package ma.tp.bank.handlers.query;

import ma.tp.bank.entities.Account;
import ma.tp.bank.entities.AccountOperation;
import ma.tp.bank.queries.GetAccountQuery;
import ma.tp.bank.queries.GetAllAccountsQuery;
import ma.tp.bank.queries.GetAccountOperationsQuery;
import ma.tp.bank.repositories.AccountRepository;
import ma.tp.bank.repositories.AccountOperationRepository;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {

    private final AccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;

    public AccountQueryHandler(
            AccountRepository accountRepository,
            AccountOperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    // Consulter un compte par son identifiant
    @QueryHandler
    public Account handle(GetAccountQuery query) {
        return accountRepository.findById(query.getAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Compte introuvable : " + query.getAccountId()));
    }

    // Consulter tous les comptes
    @QueryHandler
    public List<Account> handle(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    // Consulter l'historique des opérations d'un compte
    @QueryHandler
    public List<AccountOperation> handle(GetAccountOperationsQuery query) {
        return operationRepository
                .findByAccountIdOrderByOperationDateDesc(query.getAccountId());
    }
}
