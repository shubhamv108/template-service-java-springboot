package code.shubham.core.keygeneration.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "available_keys")
public class AvailableKey {

	@Id
	@Column(unique = true, nullable = false, length = 8)
	private String name;

	private Boolean isSelected;

}
