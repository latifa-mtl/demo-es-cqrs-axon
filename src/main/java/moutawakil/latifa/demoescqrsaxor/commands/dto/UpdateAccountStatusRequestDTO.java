package moutawakil.latifa.demoescqrsaxor.commands.dto;

import moutawakil.latifa.demoescqrsaxor.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}