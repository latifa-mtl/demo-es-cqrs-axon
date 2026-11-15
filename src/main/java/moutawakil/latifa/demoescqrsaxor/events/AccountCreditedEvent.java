package moutawakil.latifa.demoescqrsaxor.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;


}
