package code.shubham.core.iammodels;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
public class GetAccountResponse {

	private AccountDTO account;

	private Collection<String> roles;

}