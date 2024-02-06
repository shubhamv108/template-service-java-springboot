package code.shubham.core.tinyurl.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "short_urls")
public class ShortURL extends BaseAbstractAuditableEntity {

	@Column(unique = true, length = 8, nullable = false, updatable = false)
	private String keyName;

	@Lob
	private String url;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryAt;

	private Long accountId;

	public String getShortURL() {
		return keyName;
	}

}
