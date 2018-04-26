package fr.mycellar.interfaces.rest.security;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.repository.jpa.user.UserRepository;
import fr.mycellar.interfaces.rest.security.dto.LoginDetails;
import fr.mycellar.security.jwt.TokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc client;

	@Before
	public void setup() {
		this.client = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}

	@Transactional
	@Rollback
	@Test
	public void login_success() throws Exception {
		User user = new User();
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmail("test@test.com");
		user.setPassword(passwordEncoder.encode("password"));
		userRepository.save(user);

		LoginDetails loginDetails = new LoginDetails();
		loginDetails.setUsername("test@test.com");
		loginDetails.setPassword("password");
		client.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDetails))).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("id_token").value(new JWSMatcher(tokenProvider)));
	}

	@Test
	public void login_failure() throws Exception {
		LoginDetails loginDetails = new LoginDetails();
		loginDetails.setUsername("test@test.com");
		loginDetails.setPassword("password");
		client.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDetails))).andExpect(status().is4xxClientError());
	}

	private static class JWSMatcher extends TypeSafeMatcher<String> {

		private TokenProvider tokenProvider;

		public JWSMatcher(TokenProvider tokenProvider) {
			this.tokenProvider = tokenProvider;
		}

		@Override
		protected boolean matchesSafely(String item) {
			return tokenProvider.getAuthentication(item) != null;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("Unknown token");
		}

	}

}
