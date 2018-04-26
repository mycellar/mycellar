package fr.mycellar.infrastructure.repository.jpa.wine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mycellar.domain.wine.Appellation;

@RepositoryRestResource
public interface AppellationRepository extends JpaRepository<Appellation, Long> {

}
