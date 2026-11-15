package moutawakil.latifa.demoescqrsaxor.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountActivatedEvent {
    private String accountId;
    private AccountStatus status;


}
