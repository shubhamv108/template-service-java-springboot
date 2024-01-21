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
@Table(name = "countries")
public class Country extends BaseAbstractDistributedIdEntity {

	@Column(nullable = false, unique = true)
	private String name;

}
