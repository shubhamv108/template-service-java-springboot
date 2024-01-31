package code.shubham.core.documentmodels;

import jakarta.validation.constraints.Max;
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
	private Long blobId;

	@NotNull
	private Long accountId;

}
