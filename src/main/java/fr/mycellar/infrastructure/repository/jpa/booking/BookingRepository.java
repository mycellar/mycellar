package fr.mycellar.infrastructure.repository.jpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mycellar.domain.booking.Booking;

@RepositoryRestResource
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
