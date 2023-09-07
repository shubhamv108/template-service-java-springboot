package code.shubham.serversentevents;

import code.shubham.commons.models.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.lang.Exception;
import io.sentry.Sentry;

@Slf4j
@RestController
@RequestMapping("/user/{userId}/events")
public class UserEventController {

	private final UserEventRepository repository;

	private final EventPublisher eventPublisher;

	@Autowired
	public UserEventController(final UserEventRepository repository, final EventPublisher eventPublisher) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping
	public ResponseEntity<?> post(@PathVariable("userId") final String userId, @RequestBody final UserEvent event)
			throws InterruptedException, ExecutionException {
		log.info(String.format("[START] Received Request: /user/%s/events; Body: %s", userId, event));

		try {
			throw new Exception("This is a test.");
		}
		catch (Exception e) {
			Sentry.captureException(e);
		}
		event.setUserId(userId);
		final UserEvent persistedAction = this.repository.save(event);
		log.info(LogMessage.of("Persisted event for userId: %s", userId));
		return this.eventPublisher.send(persistedAction)
			.thenApply(e -> ResponseEntity.of(Optional.ofNullable(persistedAction)))
			.thenApply(e -> {
				log.info(LogMessage.of("[COMPLETE] Responding Request: /user/%s/events; Response: %s", userId, e));
				return e;
			})
			.get();
	}

	@GetMapping
	public ResponseEntity<?> get(@PathVariable("userId") final String userId)
			throws InterruptedException, ExecutionException {
		return ResponseEntity.of(Optional.ofNullable(this.repository.findByUserId(userId)));
	}

}
