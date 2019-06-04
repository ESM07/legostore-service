package com.eduardo.example.legostore;

import com.eduardo.example.legostore.model.*;
import com.eduardo.example.legostore.repository.LegoSetRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LegostoreDatabaseTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LegoSetRepository legoSetRepository;

    List<LegoSet> legoSets;

    @Before
    public void before() {
        legoSetRepository.deleteAll();

        PaymentOptions creditCardPayment = new PaymentOptions(PaymentType.CreditCard, 0);
        PaymentOptions payPalPayment = new PaymentOptions(PaymentType.PayPal, 1);
        PaymentOptions cashPayment = new PaymentOptions(PaymentType.Cash, 10);
        mongoTemplate.insert(creditCardPayment);
        mongoTemplate.insert(payPalPayment);
        mongoTemplate.insert(cashPayment);
        LegoSet milleniumFalcon = new LegoSet("Millennium Falcon", LegoSetDifficulty.HARD, "Star Wars",
                Arrays.asList(
                        new ProductReview("Dan", 7),
                        new ProductReview("Anna", 10),
                        new ProductReview("John", 8)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, false), creditCardPayment);

        LegoSet skyPolice = new LegoSet("Sky Police Air Base", LegoSetDifficulty.MEDIUM, "City",
                Arrays.asList(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true), payPalPayment);

        legoSetRepository.insert(milleniumFalcon);
        legoSetRepository.insert(skyPolice);
        legoSets = new ArrayList<>();

    }

    @Test
    public void findAllWhereReviewRatingEqualsTest() {
        legoSets = legoSetRepository.findAllWhereReviewRatingEquals(10);
        assertEquals("Name doesn't match", "Millennium Falcon", legoSets.get(0).getName());
        assertEquals(1, legoSets.size());

    }

    @Test
    public void findAllProductsInStockTest() {
        legoSets = legoSetRepository.findAllProductsInStock();
        assertEquals(1, legoSets.size());
        assertTrue(legoSets.get(0).getDeliveryInfo().isInStock());
    }

//    @Test
//    void testGetByPaymentOption() {
//
//    }


}
