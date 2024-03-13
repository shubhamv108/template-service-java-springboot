package code.shubham.commons.aws.sqs;

import code.shubham.commons.models.Event;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SQSMessageSender {

	@Value("${aws.sqs.queue.name}")
	private String queueName;

	private final QueueMessagingTemplate messagingTemplate;

	public void send(final Event message) {
		this.send(this.queueName, message);
	}

	public void send(final String queueName, final Event message) {
		this.messagingTemplate.convertAndSend(queueName, message, this.headers());
	}

	private Map<String, Object> headers() {
		return Map.of();
	}

}
