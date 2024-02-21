package code.shubham.core.pastebinmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePasteRequest {

	private Long blobId;

	private String alias;

	@Builder.Default
	private Long ttlInSeconds = 10000000L;

}
