package code.shubham.core.documentstoremodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SaveDocumentRequest {

	@NotNull
	@NotEmpty
	@Max(64)
	private String name;

	@NotNull
	@NotEmpty
	@Min(36)
	@Max(36)
	private Long blobId;

	@NotNull
	@NotEmpty
	@Min(17)
	@Max(19)
	private Long userId;

}
