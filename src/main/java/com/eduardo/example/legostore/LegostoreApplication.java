package com.eduardo.example.legostore;

import com.eduardo.example.legostore.model.*;
import com.eduardo.example.legostore.repository.LegoSetRepository;
import com.eduardo.example.legostore.repository.LegoSetRepositoryCustom;
import com.eduardo.example.legostore.repository.LegoSetRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class LegostoreApplication implements CommandLineRunner{
    @Autowired
    LegoSetRepository legoSetRepository;

    @Bean
    public LegoSetRepositoryCustomImpl legoSetRepositoryCustom() {
        return new LegoSetRepositoryCustomImpl();
    }


    public static void main(String[] args) {
        SpringApplication.run(LegostoreApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        legoSetRepository.deleteAll();

        PaymentOptions creditCardPayment = new PaymentOptions(PaymentType.CreditCard, 0);
        PaymentOptions payPalPayment = new PaymentOptions(PaymentType.PayPal, 1);
        PaymentOptions cashPayment = new PaymentOptions(PaymentType.Cash, 10);

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
    }
}
