package code.shubham.core.accountprofiles.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.accountprofiles.dao.entities.AccountProfile;
import code.shubham.core.accountprofiles.dao.repositories.AccountProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountProfileService {

	private final AccountProfileRepository repository;

	public AccountProfile update(final Long accountId, final String address) {
		return this.repository.save(this.repository.findByAccountId(accountId).map(profile -> {
			profile.setAddress(address);
			return profile;
		}).orElse(AccountProfile.builder().accountId(accountId).address(address).build()));
	}

	public String getAddress(final Long accountId) {
		return this.fetchByAccountId(accountId).getAddress();
	}

	public AccountProfile fetchByAccountId(final Long accountId) {
		return this.repository.findByAccountId(accountId)
			.orElseThrow(() -> new InvalidRequestException("accountId", "No account profile found for accountId: %s",
					String.valueOf(accountId)));
	}

}
