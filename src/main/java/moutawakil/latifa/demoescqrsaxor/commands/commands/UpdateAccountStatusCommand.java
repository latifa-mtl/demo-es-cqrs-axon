package moutawakil.latifa.demoescqrsaxor.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;

}
