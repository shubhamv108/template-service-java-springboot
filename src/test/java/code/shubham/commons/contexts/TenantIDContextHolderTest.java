package code.shubham.commons.contexts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TenantIDContextHolderTest {

	@Test
	void test_set_get_clear_Tenant() {
		TenantIDContextHolder.set("id");

		Assertions.assertEquals("id", TenantIDContextHolder.get());

		TenantIDContextHolder.clear();

		Assertions.assertNull(TenantIDContextHolder.get());
	}

}