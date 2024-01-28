package code.shubham.core.iamcommons;

import code.shubham.core.iammodels.GetOrCreateAccount;
import code.shubham.core.iammodels.GetAccountResponse;

public interface IAccountService {

	GetAccountResponse getOrCreate(GetOrCreateAccount.Request request);

	GetAccountResponse getById(Long accountId);

}
