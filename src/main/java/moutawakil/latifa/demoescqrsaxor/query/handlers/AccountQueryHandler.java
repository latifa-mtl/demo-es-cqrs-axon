package moutawakil.latifa.demoescqrsaxor.query.handlers;

import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import moutawakil.latifa.demoescqrsaxor.query.entities.Operation;
import moutawakil.latifa.demoescqrsaxor.query.queries.GetAllAccountsQuery;
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

}
