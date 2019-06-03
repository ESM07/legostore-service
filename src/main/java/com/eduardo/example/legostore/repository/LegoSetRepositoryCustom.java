package com.eduardo.example.legostore.repository;

import com.eduardo.example.legostore.model.AvgRatingModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LegoSetRepositoryCustom {

    List<AvgRatingModel> findAllAverageRatings();
}
