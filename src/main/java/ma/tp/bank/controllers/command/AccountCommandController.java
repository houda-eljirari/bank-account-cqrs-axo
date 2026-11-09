package ma.tp.bank.controllers.command;

import ma.tp.bank.commands.AddAccountCommand;
import ma.tp.bank.commands.CreditAccountCommand;
import ma.tp.bank.commands.DebitAccountCommand;
import ma.tp.bank.commands.UpdateAccountStatusCommand;

import ma.tp.bank.dto.CreateAccountRequest;
import ma.tp.bank.dto.AmountRequest;
import ma.tp.bank.dto.UpdateAccountStatusRequest;

import jakarta.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/accounts")
public class AccountCommandController {

    private final CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    // Créer un compte
    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        String accountId = UUID.randomUUID().toString();

        return commandGateway.send(
                new AddAccountCommand(
                        accountId,
                        request.getInitialBalance()
                )
        ).thenApply(result ->
                ResponseEntity.ok(accountId)
        );
    }

    // Créditer un compte
    @PostMapping("/{id}/credit")
    public CompletableFuture<ResponseEntity<String>> creditAccount(
            @PathVariable String id,
            @Valid @RequestBody AmountRequest request) {

        return commandGateway.send(
                new CreditAccountCommand(id, request.getAmount())
        ).thenApply(result ->
                ResponseEntity.ok("Compte crédité avec succès")
        );
    }

    // Débiter un compte
    @PostMapping("/{id}/debit")
    public CompletableFuture<ResponseEntity<String>> debitAccount(
            @PathVariable String id,
            @Valid @RequestBody AmountRequest request) {

        return commandGateway.send(
                new DebitAccountCommand(id, request.getAmount())
        ).thenApply(result ->
                ResponseEntity.ok("Compte débité avec succès")
        );
    }

    // Modifier le statut
    @PutMapping("/{id}/status")
    public CompletableFuture<ResponseEntity<String>> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateAccountStatusRequest request) {

        return commandGateway.send(
                new UpdateAccountStatusCommand(id, request.getStatus())
        ).thenApply(result ->
                ResponseEntity.ok("Statut du compte modifié avec succès")
        );
    }
}
