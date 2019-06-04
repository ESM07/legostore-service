package com.eduardo.example.legostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PaymentOptions {
    @Id
    private String id;
    private PaymentType paymentType;
    private int fee;

    public PaymentOptions(PaymentType paymentType, int fee) {
        this.paymentType = paymentType;
        this.fee = fee;
    }


}
