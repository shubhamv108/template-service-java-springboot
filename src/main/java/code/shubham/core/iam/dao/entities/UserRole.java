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
@Table(name = "users_roles", indexes = { @Index(name = "index_users_roles_userId", columnList = "userId") },
		uniqueConstraints = {
				@UniqueConstraint(name = "UK_users_roles_userId__role", columnNames = { "userId", "role" }) })
public class UserRole extends BaseAbstractIdEntity {

	@Column(nullable = false)
	private Long userId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private Role role;

}