package moutawakil.latifa.demoescqrsaxor.query.entities;
import jakarta.persistence.*;
import lombok.*;
import moutawakil.latifa.demoescqrsaxor.enums.OperationType;

import java.util.Date;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private Account account;
}