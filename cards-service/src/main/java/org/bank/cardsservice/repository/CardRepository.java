package org.bank.cardsservice.repository;

import org.bank.cardsservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardHolder(String cardHolder);
}
