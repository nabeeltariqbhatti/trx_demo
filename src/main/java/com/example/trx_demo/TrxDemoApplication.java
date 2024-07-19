package com.example.trx_demo;

import com.example.trx_demo.domain.Card;
import com.example.trx_demo.domain.CardRepository;
import com.example.trx_demo.domain.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TrxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrxDemoApplication.class, args);
    }


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {

        System.out.println("Application ready");
//        createCardsAndMeasureTime();
        getAllCards();

    }

    @Transactional
    public void getAllCards() {
        System.out.println("Fetching all cards from the database...");
        long startTime = System.currentTimeMillis();

        List<Card> cards = cardRepository.findAll();
        System.out.println(cards.size());

        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;
        System.out.println("Time taken to fetch cards: " + durationMs / 1000f + " ms");

    }

    @PersistenceContext
    private EntityManager entityManager;



    @Transactional
    public void createCardsAndMeasureTime() {
        long startTime = System.currentTimeMillis();

        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Card card = new Card("CardNumber-" + i);
//            card.setTransactions(new ArrayList<>());
//            Transaction transaction = new Transaction("Transaction " + i, 100.0 * (i + 1), card);
//            card.getTransactions().add(transaction);
//            transaction.setCard(card);
            cards.add(card);
        }
        cardRepository.saveAll(cards);
        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;
        System.out.println("Time taken to save 100,000 cards: " + durationMs / 1000f + " ms");
    }

//    @Transactional
//    public Card createCardWithTransactions(String cardNumber) {
//        // Create a new card
//        Card card = new Card(cardNumber);
//
//        // Create transactions for the card
//        List<Transaction> transactions = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Transaction transaction = new Transaction("Transaction " + i, 100.0 * (i + 1), card);
//            transactions.add(transaction);
//        }
//
//        // Set transactions in the card
//        card.setTransactions(transactions);
//
//        // Save the card (and its transactions) to the database
//        // Assuming you have autowired CardRepository and TransactionRepository
//
//        // Save card
//        cardRepository.save(card);
//
//        // Save transactions
//        transactionRepository.saveAll(transactions);
//
//        return card;
//    }

}
