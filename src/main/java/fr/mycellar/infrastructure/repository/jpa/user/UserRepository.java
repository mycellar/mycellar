package fr.mycellar.infrastructure.repository.jpa.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mycellar.domain.user.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmail(String email);

}
