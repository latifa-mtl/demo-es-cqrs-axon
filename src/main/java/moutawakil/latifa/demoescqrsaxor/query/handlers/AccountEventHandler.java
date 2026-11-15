package moutawakil.latifa.demoescqrsaxor.query.handlers;


import lombok.extern.slf4j.Slf4j;

import moutawakil.latifa.demoescqrsaxor.enums.OperationType;
import moutawakil.latifa.demoescqrsaxor.events.*;
import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import moutawakil.latifa.demoescqrsaxor.query.entities.Operation;
import moutawakil.latifa.demoescqrsaxor.query.repository.AccountRepository;
import moutawakil.latifa.demoescqrsaxor.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }


    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("################# Query side AccountCreatedEvent received################");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("################# Query side AccountActivatedEvent received################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event, EventMessage eventMessage){
        log.info("################# Query side AccountStatusUpdatedEvent received################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("################# Query side AccountDebitedEvent received################");
        Account account = accountRepository.findById(event.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Operation operation = Operation.builder()
                .date(eventMessage.getTimestamp())
                .amount(event.getAmount())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-operation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, operation);
    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("################# Query side AccountCreditedEvent received################");
        Account account = accountRepository.findById(event.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Operation operation = Operation.builder()
                .date(eventMessage.getTimestamp())
                .amount(event.getAmount())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+operation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, operation);
    }


}
