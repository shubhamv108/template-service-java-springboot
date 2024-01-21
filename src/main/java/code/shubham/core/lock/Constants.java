package code.shubham.core.lock;

public interface Constants {

	interface Queries {

		String INSERT = "INSERT INTO locks VALUES (?, ?, ?, ?, TIMESTAMPADD(SECOND, ?, NOW()))";

		String LOCK = "UPDATE locks SET owner = ?, expiry_at = TIMESTAMPADD(SECOND, ?, NOW()), version = version + 1 WHERE name = ? AND version = ? AND (expiry_at is null OR expiry_at < NOW())";

		String RENEW = "UPDATE locks SET expiry_at = TIMESTAMPADD(SECOND, ?, expiry_at), version = version + 1 WHERE name = ? AND owner = ? AND version = ? AND now() < expiry_at";

		String UNLOCK = "UPDATE locks SET owner = null, expiry_at = now(), version = version + 1 WHERE name = ? AND owner = ? AND version = ?";

	}

}