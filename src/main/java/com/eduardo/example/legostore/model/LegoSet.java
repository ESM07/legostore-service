package com.eduardo.example.legostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;

@Data
@NoArgsConstructor

@Document(collection = "legosets")
public class LegoSet {
    @Id
    private String id;
    @TextIndexed
    private String name;

    private LegoSetDifficulty legoSetDifficulty;
    @TextIndexed
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;
    private Collection<ProductReview> reviews;
    private DeliveryInfo deliveryInfo;
    @Transient
    private int numParts;
    @DBRef
    private PaymentOptions paymentOptions;

    public LegoSet(String name, LegoSetDifficulty legoSetDifficulty, String theme, Collection<ProductReview> reviews, DeliveryInfo deliveryInfo, PaymentOptions paymentOptions) {
        this.name = name;
        this.legoSetDifficulty = legoSetDifficulty;
        this.theme = theme;
        this.reviews = reviews;
        this.deliveryInfo = deliveryInfo;
        this.paymentOptions = paymentOptions;
    }
}
