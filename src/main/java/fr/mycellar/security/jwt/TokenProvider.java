package fr.mycellar.security.jwt;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import fr.mycellar.configuration.MyCellarProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";

	private String secretKey;

	private long tokenValidityInMilliseconds;

	private long tokenValidityForRememberMeInMilliseconds;

	private final MyCellarProperties myCellarProperties;

	public TokenProvider(MyCellarProperties myCellarProperties) {
		this.myCellarProperties = myCellarProperties;
	}

	@PostConstruct
	public void init() {
		this.secretKey = myCellarProperties.getJwtSecret();

		this.tokenValidityInMilliseconds = 1000 * 3600 / 4;
		this.tokenValidityForRememberMeInMilliseconds = 1000 * 3600 * 48;
	}

	public String createToken(Authentication authentication, boolean rememberMe) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = Instant.now().toEpochMilli();
		long validity;
		if (rememberMe) {
			validity = now + this.tokenValidityForRememberMeInMilliseconds;
		} else {
			validity = now + this.tokenValidityInMilliseconds;
		}

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(new Date(validity)).compact();
	}

	public Authentication getAuthentication(String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		Jws<Claims> jws = getJwsClaims(token);
		if (jws == null) {
			return null;
		}
		Claims claims = jws.getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).filter(StringUtils::hasText)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	private Jws<Claims> getJwsClaims(String authToken) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
		} catch (SignatureException e) {
			log.info("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return null;
	}
}