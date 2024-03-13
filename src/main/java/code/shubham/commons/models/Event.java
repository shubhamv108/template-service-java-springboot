package code.shubham.commons.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private String eventName;

	private String eventType;

	private String data;

	private String uniqueReferenceId; // idempotency key

	private Long userId;

	private Date createdAt;

	private String correlationId; // traceId, unique for each chain of events

	@Override
	public String toString() {
		return String.format("name: %s, type: %s, correlation_id: %s", eventName, eventType, correlationId);
	}

}
