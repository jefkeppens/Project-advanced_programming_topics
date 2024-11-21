package fact.it.ticket.repository;

import fact.it.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTicketNumber(String ticketNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Ticket t SET t = :ticket WHERE t.ticketNumber = :ticketNumber")
    void updateByTicketNumber(@Param("ticketNumber") String ticketNumber, @Param("ticket") Ticket ticket);
}
