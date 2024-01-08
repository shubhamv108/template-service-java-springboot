package code.shubham.core.lockmodels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LockRequestDTO {

	@NotNull
	@NotEmpty
	private String owner;

	@NotEmpty
	@NotNull
	private Long timeToLiveInSeconds;

	@NotNull
	@NotEmpty
	private Integer previousVersion = 0;

}
