package moutawakil.latifa.demoescqrsaxor.commands.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}