package code.shubham.core.accountprofiles.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_profiles")
public class AccountProfile extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private Long accountId;

	private String address;

}
