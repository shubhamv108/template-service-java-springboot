package code.shubham.templateservicejavaspringboot;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Component
public class UserEventSubscriber {

	private final EmitterProcessor<UserEvent> emitter = EmitterProcessor.create();

	public Flux<UserEvent> get() {
		return emitter.log();
	}

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}")
	public void onReceive(final String data) {
		this.emitter.onNext(JsonUtils.as(data, UserEvent.class));
	}

}