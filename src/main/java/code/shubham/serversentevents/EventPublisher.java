package code.shubham.serversentevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EventPublisher {

	@Value("${kafka.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public EventPublisher(final KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public <T> CompletableFuture<SendResult<String, String>> send(T object) {
		return this.kafkaTemplate.send(this.topicName, JsonUtils.get(object));
	}

}
