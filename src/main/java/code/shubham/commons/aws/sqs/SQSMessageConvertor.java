package code.shubham.commons.aws.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
public class SQSMessageConvertor implements MessageConverter {

	private final ObjectMapper objectMapper;

	public SQSMessageConvertor(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Object fromMessage(final Message<?> message, Class<?> targetClass) {
		final Object payload = message.getPayload();
		return ClassUtils.isAssignableValue(targetClass, payload) ? payload : null;
	}

	@Override
	public Message<?> toMessage(final Object payload, final MessageHeaders headers) {
		if (headers != null) {
			MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(headers, MessageHeaderAccessor.class);
			if (accessor != null && accessor.isMutable()) {
				return MessageBuilder.createMessage(getStringPayload(payload), accessor.getMessageHeaders());
			}
		}

		return MessageBuilder.withPayload(getStringPayload(payload)).copyHeaders(headers).build();
	}

	private String getStringPayload(final Object payload) {
		try {
			return this.objectMapper.writeValueAsString(payload);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
