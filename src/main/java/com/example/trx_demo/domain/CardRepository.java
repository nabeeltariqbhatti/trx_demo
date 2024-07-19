package com.example.trx_demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "select count(card) from Card  card")
    long count();
}
