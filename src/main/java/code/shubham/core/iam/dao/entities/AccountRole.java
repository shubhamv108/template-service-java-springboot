package code.shubham.core.iam.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractIdEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "accounts_roles", indexes = { @Index(name = "index_users_roles_accountId", columnList = "accountId") },
		uniqueConstraints = {
				@UniqueConstraint(name = "UK_accounts_roles_accountId__role", columnNames = { "accountId", "role" }) })
public class AccountRole extends BaseAbstractIdEntity {

	@Column(nullable = false)
	private Long accountId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private Role role;

}