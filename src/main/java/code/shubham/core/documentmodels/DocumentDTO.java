package code.shubham.core.documentmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DocumentDTO {

	private Long documentId;

	private String name;

	private String owner;

}
