package fr.mycellar.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.repository.jpa.user.UserRepository;

@Component("userDetailsService")
public class DatabaseUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

	private final UserRepository userRepository;

	public DatabaseUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);

		Optional<User> userByEmailFromDatabase = userRepository.findOneWithAuthoritiesByEmail(login);
		return userByEmailFromDatabase.map(user -> createSpringSecurityUser(login, user)).orElseThrow(
				() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin,
			User user) {
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
}