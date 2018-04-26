package fr.mycellar.infrastructure.repository.jpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mycellar.domain.booking.BookingEvent;

@RepositoryRestResource
public interface BookingEventRepository extends JpaRepository<BookingEvent, Long> {

}
