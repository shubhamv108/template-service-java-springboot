package code.shubham.core.blobstoremodels;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateBlobResponse {

	@NotNull
	private Long blobId;

	@NotNull
	private String uploadUrl;

}
