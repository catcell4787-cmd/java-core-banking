package org.bank.cardsservice.repository;

import org.bank.cardsservice.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardHolder(String cardHolder);

    boolean existsByCardHolder(String cardHolder);
}
