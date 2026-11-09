package moutawakil.latifa.demoescqrsaxor.commands.aggregates;

import moutawakil.latifa.demoescqrsaxor.commands.commands.AddAccountCommand;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;
import moutawakil.latifa.demoescqrsaxor.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Default constructor required by Axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        if(command.getInitialBalance() < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));

    }
}
