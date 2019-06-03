package com.eduardo.example.legostore.repository;

import com.eduardo.example.legostore.model.AvgRatingModel;
import com.eduardo.example.legostore.model.LegoSet;
import com.eduardo.example.legostore.model.LegoSetDifficulty;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet>, LegoSetRepositoryCustom {
    List<LegoSet> findAllByThemeContains(String theme);

    List<LegoSet> findAllByThemeContains(String theme, Sort sort);

    List<LegoSet> findByLegoSetDifficultyAndNameStartsWith(LegoSetDifficulty legoSetDifficulty, String name);

    @Query(value = "{'deliveryInfo.deliveryFee':{$lt:?0}}", sort = "{'theme':1}")
    List<LegoSet> findAllWhereDeliveryFeeIsLessThan(int price);

    @Query("{'reviews.rating':{$eq:?0}}")
    List<LegoSet> findAllWhereReviewRatingEquals(int price);

    List<LegoSet> findAllByThemeIsNot(String theme);

    @Query("{'deliveryInfo.inStock':true}")
    List<LegoSet> findAllProductsInStock();

    List<LegoSet> findAllBy(TextCriteria textCriteria);

}
