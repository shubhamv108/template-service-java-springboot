package code.shubham.core.pastebin.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pastes", indexes = { @Index(name = "index_account_id", columnList = "accountId") })
public class Paste extends BaseAbstractAuditableEntity {

	@Column(length = 8, unique = true, nullable = false, updatable = false)
	private String keyName; // 8 bytes

	private Long blobId; // 32 bits

	private Date expiryAt; // 5 bytes

	private Long accountId; // 32 bytes

}
