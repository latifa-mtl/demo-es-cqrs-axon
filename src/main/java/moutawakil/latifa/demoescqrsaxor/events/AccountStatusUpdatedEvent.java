package moutawakil.latifa.demoescqrsaxor.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;


}
