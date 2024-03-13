package code.shubham.commons.aws.sns;

import code.shubham.commons.models.Event;
import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.config.annotation.NotificationSubject;
import io.awspring.cloud.messaging.endpoint.NotificationStatus;
import io.awspring.cloud.messaging.endpoint.annotation.NotificationMessageMapping;
import io.awspring.cloud.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import io.awspring.cloud.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/default-topic-subscriber")
public class DefaultSNSEndpointController {

	@NotificationSubscriptionMapping // x-amz-sns-message-type=SubscriptionConfirmation
	public void confirmUnsubscribeMessage(final NotificationStatus notificationStatus) {
		notificationStatus.confirmSubscription();
	}

	@NotificationMessageMapping // header [x-amz-sns-message-type=Notification].
	public void receiveNotification(@NotificationMessage final Event message,
			@NotificationSubject final String subject) {
		System.out.println("SNSNotification: " + message);
	}

	@NotificationUnsubscribeConfirmationMapping // header
												// [x-amz-sns-message-type=UnsubscribeConfirmation].
	public void confirmSubscriptionMessage(final NotificationStatus notificationStatus) {
		notificationStatus.confirmSubscription();
	}

}
