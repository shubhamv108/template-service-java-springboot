package code.shubham.core.lockmodels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UnlockRequestDTO {

	@NotNull
	@NotEmpty
	private String owner;

	@NotNull
	@NotEmpty
	private Integer version;

}
