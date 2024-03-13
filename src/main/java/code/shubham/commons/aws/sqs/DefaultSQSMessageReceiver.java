package code.shubham.commons.aws.sqs;

import code.shubham.commons.models.Event;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
// @ConditionalOnProperty(prefix = "service", name = "module", havingValue = "worker")
public class DefaultSQSMessageReceiver {

	@Value("${aws.sqs.queue.name}")
	private String queueName;

	@MessageMapping
	@SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessage(@Payload final Event message, @Headers final Map<String, Object> headers) {
		System.out.println(message);
	}

}
