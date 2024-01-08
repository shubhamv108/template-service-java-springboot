package code.shubham.core.lock.web.v1.dao.entites;

import code.shubham.commons.dao.entities.base.BaseIdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "locks")
public class Lock extends BaseIdEntity {

	@Column(nullable = false, unique = true, length = 64)
	private String name;

	@Column(length = 64, nullable = false)
	private String owner;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_at")
	private Date expiryAt;

	@Builder.Default
	@Version
	private Integer version = 0;

}