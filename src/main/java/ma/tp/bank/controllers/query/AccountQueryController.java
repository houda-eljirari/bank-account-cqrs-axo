
package ma.tp.bank.controllers.query;

import ma.tp.bank.entities.Account;
import ma.tp.bank.entities.AccountOperation;
import ma.tp.bank.queries.GetAccountQuery;
import ma.tp.bank.queries.GetAllAccountsQuery;
import ma.tp.bank.queries.GetAccountOperationsQuery;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/accounts")
public class AccountQueryController {

    private final QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    // Consulter un compte par son identifiant
    @GetMapping("/{id}")
    public CompletableFuture<Account> getAccount(@PathVariable String id) {
        return queryGateway.query(
                new GetAccountQuery(id),
                Account.class
        );
    }

    // Consulter tous les comptes
    @GetMapping
    public CompletableFuture<List<Account>> getAllAccounts() {
        return queryGateway.query(
                new GetAllAccountsQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(Account.class)
        );
    }

    // Consulter l'historique des opérations d'un compte
    @GetMapping("/{id}/operations")
    public CompletableFuture<List<AccountOperation>> getAccountOperations(
            @PathVariable String id) {

        return queryGateway.query(
                new GetAccountOperationsQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(AccountOperation.class)
        );
    }
}
