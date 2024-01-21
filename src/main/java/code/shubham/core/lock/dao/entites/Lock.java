package code.shubham.core.lock.dao.entites;

import code.shubham.commons.dao.base.entities.BaseAbstractDistributedIdEntity;
import code.shubham.commons.dao.base.entities.BaseAbstractIdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "locks")
public class Lock extends BaseAbstractDistributedIdEntity {

	@Column(nullable = false, unique = true, length = 64)
	private String name;

	@Column(length = 64, nullable = false)
	private String owner;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_at")
	private Date expiryAt;

	@Version
	private Integer version;

}