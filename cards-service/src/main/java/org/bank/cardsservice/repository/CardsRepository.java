package org.bank.cardsservice.repository;

import org.bank.cardsservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<Card, Long> {
    boolean existsByCardHolder(String cardHolder);
}
