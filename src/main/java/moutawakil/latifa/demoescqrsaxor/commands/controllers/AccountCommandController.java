package moutawakil.latifa.demoescqrsaxor.commands.controllers;


import moutawakil.latifa.demoescqrsaxor.commands.commands.AddAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.CreditAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.DebitAccountCommand;
import moutawakil.latifa.demoescqrsaxor.commands.commands.UpdateAccountStatusCommand;
import moutawakil.latifa.demoescqrsaxor.commands.dto.CreditAccountRequestDTO;
import moutawakil.latifa.demoescqrsaxor.commands.dto.DebitAccountRequestDTO;
import moutawakil.latifa.demoescqrsaxor.commands.dto.UpdateAccountStatusRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;
import moutawakil.latifa.demoescqrsaxor.commands.dto.AddNewAccountRequestDTO;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }


    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateAccountStatus(@RequestBody UpdateAccountStatusRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(),
                request.status()
        ));
        return result;

    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {
        return ex.getMessage();
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
}
