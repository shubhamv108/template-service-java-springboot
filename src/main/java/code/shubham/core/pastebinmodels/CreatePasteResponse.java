package code.shubham.core.pastebinmodels;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CreatePasteResponse {

	private String shortUrl;

	private Date uploadUrlExpiryAt;

}
