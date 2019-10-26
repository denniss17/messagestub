package nl.dennisschroer.messagestub.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeMessageRepository extends JpaRepository<ExchangeMessage, Long> {
}
