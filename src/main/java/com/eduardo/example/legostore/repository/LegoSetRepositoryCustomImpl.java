package com.eduardo.example.legostore.repository;

import com.eduardo.example.legostore.model.AvgRatingModel;
import com.eduardo.example.legostore.model.LegoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

public class LegoSetRepositoryCustomImpl implements LegoSetRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<AvgRatingModel> findAllAverageRatings() {
        ProjectionOperation projectionOperation = project().andExpression("$name").as("legoSetName")
                .andExpression("{$avg:'$reviews.rating'}").as("avgRating");
        Aggregation aggregationPipeline = newAggregation(projectionOperation);

        return mongoTemplate.aggregate(aggregationPipeline,LegoSet.class,AvgRatingModel.class).getMappedResults();
    }
}
