package code.shubham.core.accountprofilemodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserProfileRequest {

	@NotNull
	@NotEmpty
	@Max(value = 256)
	private String address;

}
