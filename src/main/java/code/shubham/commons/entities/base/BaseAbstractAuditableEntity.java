package code.shubham.commons.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class BaseAbstractAuditableEntity implements Serializable {

	private static final long serialVersionUID = 8953324502234883513L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	private Date updatedAt;

	@JsonIgnore
	@Column(name = "created_by", nullable = false, updatable = false)
	@CreatedBy
	private Integer createdBy;

	@JsonIgnore
	@Column(name = "updated_by", nullable = false)
	@LastModifiedBy
	private Integer updatedBy;

	@Builder.Default
	@JsonIgnore
	@Version
	private Integer version = 0;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BaseAbstractAuditableEntity that = (BaseAbstractAuditableEntity) o;
		return this.id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
