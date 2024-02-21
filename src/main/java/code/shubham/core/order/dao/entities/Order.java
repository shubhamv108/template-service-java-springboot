package code.shubham.core.order.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders", indexes = { @Index(name = "index_orders_account_id", columnList = "accountId") })
public class Order extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String customerId;

	@Column(nullable = false)
	private String customerType;

	@Column(nullable = false)
	private Long accountId;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(nullable = false, unique = true)
	private String clientUniqueReferenceId;

}
