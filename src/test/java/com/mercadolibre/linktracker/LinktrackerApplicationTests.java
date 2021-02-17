package com.mercadolibre.linktracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.RedirectView;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LinktrackerApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	}

	private static Stream<Arguments> createLinkTest_parameters(){
		return Stream.of(
				Arguments.of(
						"{\"url\":\"https://www.google.com\",\"password\":\"1234\"}",
						"{\"id\":1,\"url\":\"https://www.google.com\",\"valid\":true}"),
				Arguments.of(
						"{\"url\":\"https://www.mercadolibre.com\",\"password\":\"\"}",
						"{\"id\":2,\"url\":\"https://www.mercadolibre.com\",\"valid\":true}")
		);
	}

	@ParameterizedTest
	@MethodSource("createLinkTest_parameters")
	void createLinkTest(String body, String response) throws Exception{
		mvc.perform(post("/link").contentType(MediaType.APPLICATION_JSON)
		.content(body))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(response)));
	}

	private static Stream<Arguments> getLinkNoPasswordTest_parameters(){
		return Stream.of(
				Arguments.of(
						"{\"url\":\"https://www.google.com\",\"password\":\"\"}",
						1,
						"https://www.google.com"),
				Arguments.of(
						"{\"url\":\"https://www.mercadolibre.com\",\"password\":\"\"}",
						2,
						"https://www.mercadolibre.com")
		);
	}

	@ParameterizedTest
	@MethodSource("getLinkNoPasswordTest_parameters")
	void getLinkNoPasswordTest(String postBody, int id, String response) throws Exception{
		post("/link").contentType(MediaType.APPLICATION_JSON).content(postBody);
		mvc.perform(get("/link/" + id))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(redirectedUrl(response));
	}

}
