package code.shubham.core.iammodels;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
public class GetUserResponse {

	private UserDTO user;

	private Collection<String> roles;

}