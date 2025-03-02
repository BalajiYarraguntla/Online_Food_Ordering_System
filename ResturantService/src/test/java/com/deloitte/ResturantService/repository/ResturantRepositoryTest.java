package com.deloitte.ResturantService.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

import com.deloitte.ResturantService.entity.Resturant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResturantRepositoryTest {

	@Autowired
	private ResturantRepository resturantRepository;

	@Autowired
	@MockBean
	private ModelMapper mapper;

	@Test
	@Transactional
	@Rollback

	public void saveResturantTest() throws JsonMappingException, JsonProcessingException {
		// Define test data
		String json = "{\n" + "    \"name\": \"Royal Hotel\",\n" + "    \"openTime\": \"09:00\",\n"
				+ "    \"closeTime\": \"22:00\",\n" + "    \"rating\": 5,\n" + "    \"address\": {\n"
				+ "        \"address1\": \"42/A, Nabanagar\",\n" + "        \"address2\": \"MLA Para\",\n"
				+ "        \"landMark\": \"Khelar Math\",\n" + "        \"city\": \"Kolkata\",\n"
				+ "        \"state\": \"West Bengal\",\n" + "        \"pincode\": \"743176\"\n" + "    },\n"
				+ "    \"foodItems\": [\n" + "        {\n" + "            \"name\": \"Chickyn\",\n"
				+ "            \"category\": \"NON_VEG\",\n" + "            \"status\": \"AVAILABLE\",\n"
				+ "            \"price\": 200,\n" + "            \"discountedPrice\": 20\n" + "        },\n"
				+ "        {\n" + "            \"name\": \"Fish\",\n" + "            \"category\": \"NON_VEG\",\n"
				+ "            \"status\": \"AVAILABLE\",\n" + "            \"price\": 340,\n"
				+ "            \"discountedPrice\": 8\n" + "        }\n" + "    ]\n" + "}";
		Resturant add = new ObjectMapper().readValue(json, Resturant.class);

		// Save the user to the database
		System.out.println("RES:\t" + add.toString());
		resturantRepository.save(add);

		// Assert that the retrieved user is not null
//		assertNotNull(savedUser);

//        // Assert that the retrieved user id is not null
//        assertNotNull(savedUser.getId());
//
//        // Assert that the retrieved user's name matches the expected name
//        assertEquals(name, savedUser.getName());
//
//        // Assert that the retrieved user's email matches the expected email
//        assertEquals(email, savedUser.getEmail());
	}

//	@DisplayName("Test 1:Register User Test")
//	public void saveEmployeeTest() throws JsonMappingException, JsonProcessingException {
//
//		Gson gson = new Gson();
//		String json = "{\n" + "    \"firstName\": \"Souvik\",\n" + "    \"middleName\": \"Kumar\",\n"
//				+ "    \"lastName\": \"Mukherjee\",\n" + "    \"password\": \"12345\",\n"
//				+ "    \"roles\": \"ADMIN\",\n" + "    \"gender\": \"MALE\",\n"
//				+ "    \"email\": \"admin@gmail.com\",\n" + "    \"phoneNumber\": \"9123213877\",\n"
//				+ "    \"address\": {\n" + "        \"address1\": \"42/A, Nabanagar\",\n"
//				+ "        \"address2\": \"MLA Para\",\n" + "        \"landMark\": \"Khelar Math\",\n"
//				+ "        \"city\": \"Kolkata\",\n" + "        \"state\": \"West Bengal\",\n"
//				+ "        \"pincode\": \"743136\"\n" + "    }\n" + "}";
//		User user = new ObjectMapper().readValue(json, User.class);
//		user = userRepositoryTest.save(user);
//		// Verify
//		System.out.println(user);
////        Assertions.assertThat(employee.getId()).isGreaterThan(0);
}