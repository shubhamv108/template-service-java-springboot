package code.shubham.commons.dao.base.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Objects;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseAbstractDistributedIdEntity implements Serializable {

	private static final long serialVersionUID = 8953224502234813513L;

	@JsonIgnore
	@Id
	@GeneratedValue(generator = "distributed-id")
	@GenericGenerator(name = "distributed-id",
			strategy = "code.shubham.commons.generators.id.hibernate.DistributedIDGenerator")
	private Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final BaseAbstractDistributedIdEntity that = (BaseAbstractDistributedIdEntity) o;
		return this.id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
