package moutawakil.latifa.demoescqrsaxor.query.dtos;

import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import moutawakil.latifa.demoescqrsaxor.query.entities.Operation;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<Operation> operations) {

}
