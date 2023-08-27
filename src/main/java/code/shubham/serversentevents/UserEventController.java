package code.shubham.serversentevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user/{userId}/events")
public class UserEventController {

    private final UserEventRepository repository;
    private final EventPublisher eventPublisher;

    @Autowired
    public UserEventController(
            final UserEventRepository repository,
            final EventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<?> post(
            @PathVariable("userId") final String userId,
            @RequestBody final UserEvent event
    ) throws InterruptedException, ExecutionException {
        event.setUserId(userId);
        final UserEvent persistedAction = this.repository.save(event);
        return this.eventPublisher
                .send(persistedAction)
                .thenApply(e ->
                        ResponseEntity.of(Optional.ofNullable(persistedAction))).get();
    }

    @GetMapping
    public ResponseEntity<?> get(
        @PathVariable("userId") final String userId
    ) throws InterruptedException, ExecutionException {
        return ResponseEntity.of(Optional.ofNullable(this.repository.findByUserId(userId)));
    }
}
