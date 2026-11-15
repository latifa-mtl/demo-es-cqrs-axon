package moutawakil.latifa.demoescqrsaxor.query.controllers;


import moutawakil.latifa.demoescqrsaxor.query.dtos.AccountStatementResponseDTO;
import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import moutawakil.latifa.demoescqrsaxor.query.queries.GetAccountStatementQuery;
import moutawakil.latifa.demoescqrsaxor.query.queries.GetAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> result = queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class));
        return result;
    }

    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId){
        return queryGateway.query(new GetAccountStatementQuery(accountId),ResponseTypes.instanceOf(AccountStatementResponseDTO.class));
    }



}
