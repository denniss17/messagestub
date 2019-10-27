package nl.dennisschroer.messagestub.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeMessageRepository extends JpaRepository<ExchangeMessage, Long> {
    List<ExchangeMessage> findAllByOrderByTimestampDesc();
}
