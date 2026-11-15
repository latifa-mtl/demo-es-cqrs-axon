package moutawakil.latifa.demoescqrsaxor.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import moutawakil.latifa.demoescqrsaxor.commands.commands.AddAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.CreditAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.DebitAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.UpdateAccountStatusCommand;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;
import moutawakil.latifa.demoescqrsaxor.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Default constructor required by Axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("########################### Add Account   command received ##########################");
        if(command.getInitialBalance() < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));

    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("########################### Account Created Event received ##########################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("########################### Account Activated Event received ##########################");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handler(CreditAccountCommand command) {
        log.info("########################### Credit Account command received ##########################");
        if (!this.status.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("This account can not be debited because of the account is not activated. The current status is "+status);
        if (command.getAmount() <= 0) throw new IllegalArgumentException("The amount to credit must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("########################### Account Credited Event occured ##########################");
        this.accountId = event.getAccountId();
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handler(DebitAccountCommand command) {
        log.info("########################### Debit Account command received ##########################");
        if (!this.status.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("This account can not be debited because of the account is not activated. The current status is "+status);
        if (balance < command.getAmount()) throw new IllegalArgumentException("Insufficient balance");
        if (command.getAmount() <= 0) throw new IllegalArgumentException("The amount to credit must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        log.info("########################### Account Debited Event occured ##########################");
        this.accountId = event.getAccountId();
        this.balance -= event.getAmount();
    }

    @CommandHandler
    public void handler(UpdateAccountStatusCommand command) {
        log.info("########################### Update Account Status command received ##########################");
        if (command.getStatus()==status) throw  new RuntimeException("This account"+command.getId()+" is already"+command.getStatus());
        AggregateLifecycle.apply(new AccountStatusUpdatedEvent(
                command.getId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountStatusUpdatedEvent event) {
        log.info("########################### Account Status Updated Event occured ##########################");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }
}
