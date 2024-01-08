package code.shubham.core.lockmodels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LockDTO {

	@NotNull
	@NotEmpty
	private Integer version;

}
