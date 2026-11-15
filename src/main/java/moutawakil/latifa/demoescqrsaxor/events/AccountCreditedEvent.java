package moutawakil.latifa.demoescqrsaxor.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountCreditedEvent {
    private String id;
    private double amount;
    private String currency;


}
