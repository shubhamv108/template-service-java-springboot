package code.shubham.core.iam.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
public class Role extends BaseAbstractAuditableEntity {

	@NaturalId
	@NotBlank
	@Column(unique = true, nullable = false, length = 60)
	private String name;

}
