package moutawakil.latifa.demoescqrsaxor.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private String currency;
    private AccountStatus status;


}
