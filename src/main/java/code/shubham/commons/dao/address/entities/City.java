package code.shubham.commons.dao.address.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractDistributedIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cities")
public class City extends BaseAbstractDistributedIdEntity {

	@Column(nullable = false)
	private String name;

	@Column
	private Long stateId;

	@Column(nullable = false, updatable = false)
	private Long countryId;

}
