package com.eduardo.example.legostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AvgRatingModel {

    private String id;
    private String legoSetName;
    private double avgRating;
}
