package code.shubham.commons.aws.sns;

import code.shubham.commons.models.Event;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SNSMessageSender {

	@Value("${aws.sqs.topic.name}")
	private String topicName;

	private final NotificationMessagingTemplate messagingTemplate;

	public void send(final Event message, final String subject) {
		this.send(this.topicName, message, subject);
	}

	public void send(final String topicName, final Event message, final String subject) {
		this.messagingTemplate.sendNotification(topicName, message, subject);
	}

}
