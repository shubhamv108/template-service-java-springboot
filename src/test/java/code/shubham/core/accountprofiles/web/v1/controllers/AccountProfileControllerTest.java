package code.shubham.core.accountprofiles.web.v1.controllers;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.core.accountprofiles.dao.entities.AccountProfile;
import code.shubham.core.accountprofiles.dao.repositories.AccountProfileRepository;
import code.shubham.core.accountprofilemodels.UpdateUserProfileRequest;
import code.shubham.test.AbstractSpringBootMVCTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountProfileControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/accounts/profiles";

	@Autowired
	private AccountProfileRepository repository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("account_profiles");
		AccountIDContextHolder.set(TestCommonConstants.ACCOUNT_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("account_profiles");
	}

	@Test
	void update_with_invalid_params_request() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"address\": [\n"
					+ "                \"address must not be empty.\"\n" + "            ]\n" + "        }\n" + "    ]\n"
					+ "}"));
	}

	@Test
	void update_Success() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"accountId\": " + TestCommonConstants.ACCOUNT_ID + ",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByAccountId(TestCommonConstants.ACCOUNT_ID);

		Assertions.assertTrue(profiles.isPresent());
		Assertions.assertEquals(TestCommonConstants.ACCOUNT_ID, profiles.get().getAccountId());
		Assertions.assertEquals("address", profiles.get().getAddress());
	}

	@Test
	void update_Success_with_existing_profile() throws Exception {
		this.repository.save(AccountProfile.builder().accountId(TestCommonConstants.ACCOUNT_ID).build());
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.baseURL + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"accountId\": " + TestCommonConstants.ACCOUNT_ID + ",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByAccountId(TestCommonConstants.ACCOUNT_ID);

		Assertions.assertTrue(profiles.isPresent());
		Assertions.assertEquals(TestCommonConstants.ACCOUNT_ID, profiles.get().getAccountId());
		Assertions.assertEquals("address", profiles.get().getAddress());
	}

	@Test
	void getByUserId_without_existing_user() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("accountId", TestCommonConstants.ACCOUNT_ID.toString())
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"accountId\": [\n"
					+ "                \"No account profile found for accountId: " + TestCommonConstants.ACCOUNT_ID + "\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var profiles = this.repository.findByAccountId(TestCommonConstants.ACCOUNT_ID);

		Assertions.assertFalse(profiles.isPresent());
	}

	@Test
	void getByUserId_with_invalid_user() throws Exception {
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("accountId", "1000897923487")
				.content(as(request)))
			.andExpect(status().is(400))
			.andExpect(content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n"
					+ "    \"error\": [\n" + "        {\n" + "            \"accountId\": [\n"
					+ "                \"Account with accountId: 1000897923487 not allowed to perform the operation\"\n"
					+ "            ]\n" + "        }\n" + "    ]\n" + "}"));

		final var profiles = this.repository.findByAccountId(TestCommonConstants.ACCOUNT_ID);

		Assertions.assertFalse(profiles.isPresent());
	}

	@Test
	void getByUserId_Success() throws Exception {
		this.repository
			.save(AccountProfile.builder().accountId(TestCommonConstants.ACCOUNT_ID).address("address").build());
		final UpdateUserProfileRequest request = new UpdateUserProfileRequest();
		request.setAddress("address");

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("accountId", TestCommonConstants.ACCOUNT_ID.toString())
				.content(as(request)))
			.andExpect(status().is(200))
			.andExpect(content().json("{\n" + "    \"statusCode\": 200,\n" + "    \"data\": {\n"
					+ "        \"accountId\": " + TestCommonConstants.ACCOUNT_ID + ",\n"
					+ "        \"address\": \"address\"\n" + "    },\n" + "    \"error\": null\n" + "}"));

		final var profiles = this.repository.findByAccountId(TestCommonConstants.ACCOUNT_ID);

		Assertions.assertTrue(profiles.isPresent());
	}

}