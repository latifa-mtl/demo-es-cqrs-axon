package moutawakil.latifa.demoescqrsaxor.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import moutawakil.latifa.demoescqrsaxor.commands.commands.AddAccountCommand;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;
import moutawakil.latifa.demoescqrsaxor.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
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

    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("########################### Account Created Event received ##########################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
