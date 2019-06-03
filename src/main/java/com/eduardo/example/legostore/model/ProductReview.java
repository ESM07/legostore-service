package com.eduardo.example.legostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    @TextIndexed
    private String userName;
    private int rating;

}
