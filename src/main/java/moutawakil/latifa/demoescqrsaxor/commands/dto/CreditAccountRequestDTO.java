package moutawakil.latifa.demoescqrsaxor.commands.dto;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}