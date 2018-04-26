package fr.mycellar.interfaces.rest.security;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.mycellar.interfaces.rest.ApiController;
import fr.mycellar.interfaces.rest.security.dto.JWTToken;
import fr.mycellar.interfaces.rest.security.dto.LoginDetails;
import fr.mycellar.security.jwt.JWTConfigurer;
import fr.mycellar.security.jwt.TokenProvider;

@RestController
public class AuthenticationController extends ApiController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManager authenticationManager;

	public AuthenticationController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<JWTToken> login(@Valid @RequestBody LoginDetails loginDetails) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDetails.getUsername(), loginDetails.getPassword());

		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginDetails.isRememberMe() == null) ? false : loginDetails.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

}
