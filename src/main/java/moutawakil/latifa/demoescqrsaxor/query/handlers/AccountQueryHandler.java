package moutawakil.latifa.demoescqrsaxor.query.handlers;

import moutawakil.latifa.demoescqrsaxor.query.dtos.AccountStatementResponseDTO;
import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import moutawakil.latifa.demoescqrsaxor.query.entities.Operation;
import moutawakil.latifa.demoescqrsaxor.query.queries.GetAccountStatementQuery;
import moutawakil.latifa.demoescqrsaxor.query.queries.GetAllAccountsQuery;
import moutawakil.latifa.demoescqrsaxor.query.queries.WatchEventQuery;
import moutawakil.latifa.demoescqrsaxor.query.repository.AccountRepository;
import moutawakil.latifa.demoescqrsaxor.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<Operation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }

    @QueryHandler
    public Operation on(WatchEventQuery query){
        return Operation.builder().build();
    }

}
