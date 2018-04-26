package fr.mycellar.infrastructure.repository.jpa.wine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mycellar.domain.wine.Wine;

@RepositoryRestResource
public interface WineRepository extends JpaRepository<Wine, Long> {

}
