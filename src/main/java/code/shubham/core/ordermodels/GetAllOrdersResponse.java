package code.shubham.core.ordermodels;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GetAllOrdersResponse {

	@Builder
	@Data
	public static class Order {

		private Long orderId;

		private Long accountId;

		private String status;

	}

	private List<Order> orders;

}
