package moutawakil.latifa.demoescqrsaxor.query.entities;
import jakarta.persistence.*;
import lombok.*;
import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id
    private String id;
    private Instant createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
}
