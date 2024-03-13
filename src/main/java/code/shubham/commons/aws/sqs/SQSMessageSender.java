package code.shubham.commons.aws.sqs;

import code.shubham.commons.models.Event;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
public class SQSMessageSender {

	@Autowired
	private QueueMessagingTemplate messagingTemplate;

	public void sendMessage(final String queueName, final Event message) {
		this.messagingTemplate.convertAndSend(queueName, message, this.headers());
	}

	private Map<String, Object> headers() {
		return Map.of();
	}

}
