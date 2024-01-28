package code.shubham.core.documentstore.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "documents",
		uniqueConstraints = { @UniqueConstraint(name = "UniqueOwnerAndName", columnNames = { "owner", "name" }) },
		indexes = { @Index(name = "index_documents_owner", columnList = "owner") })
public class Document extends BaseAbstractAuditableEntity {

	@Column(nullable = false, length = 24)
	private String name;

	@Column(nullable = false)
	private Long owner;

	@Column(nullable = false)
	private Long blobId;

}
