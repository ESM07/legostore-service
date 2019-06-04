package com.eduardo.example.legostore.controller;

import com.eduardo.example.legostore.model.AvgRatingModel;
import com.eduardo.example.legostore.model.LegoSet;
import com.eduardo.example.legostore.model.LegoSetDifficulty;
import com.eduardo.example.legostore.model.QLegoSet;
import com.eduardo.example.legostore.repository.LegoSetRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("legosets/api")
public class LegoSetController {

    @Autowired
    private LegoSetRepository legoSetRepository;


    @PostMapping
    public void addLegoSet(@RequestBody LegoSet legoSet) {
        legoSetRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public List<LegoSet> findAllSets() {
        Sort sort = Sort.by("theme").ascending();
        return legoSetRepository.findAll(sort);
    }

    @PutMapping
    public void updateSet(@RequestBody LegoSet legoSet) {
        legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void deleteSet(@PathVariable String id) {
        legoSetRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet findSetById(@PathVariable String id) {
        return legoSetRepository.findById(id).orElse(null);
    }

    @GetMapping("/theme/{theme}")
    public List<LegoSet> findSetByTheme(@PathVariable String theme) {
        Sort sort = Sort.by("theme").ascending();
        return legoSetRepository.findAllByThemeContains(theme, sort);
    }

    @GetMapping("/hardThatStartWithM")
    public List<LegoSet> hardThatStartWithM() {
        return legoSetRepository.findByLegoSetDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("/deliveryFeeLessThan/{price}")
    public List<LegoSet> deliveryFeeLessThan(@PathVariable int price) {
        return legoSetRepository.findAllWhereDeliveryFeeIsLessThan(price);
    }

    @GetMapping("/themesNotEqual/{theme}")
    public List<LegoSet> themesNotEqual(@PathVariable String theme) {
        return legoSetRepository.findAllByThemeIsNot(theme);
    }

    @GetMapping("/reviewRatingEqualsTo/{rating}")
    public List<LegoSet> reviewRatingEqualsTo(@PathVariable int rating) {
        return legoSetRepository.findAllWhereReviewRatingEquals(rating);
    }

    @GetMapping("/productsInStock")
    public List<LegoSet> findProductsInStock() {
        return legoSetRepository.findAllProductsInStock();
    }

    //Get products in stock, with delivery fee less than 50, and rated 10
    @GetMapping("bestProducts")
    public List<LegoSet> getBestProducts(){
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        Predicate feeFilter = query.deliveryInfo.deliveryFee.lt(50);
        Predicate rateFilter = query.reviews.any().rating.eq(10);

        Predicate bestProductsFilter = inStockFilter.and(feeFilter).and(rateFilter);
        return (List<LegoSet>) legoSetRepository.findAll(bestProductsFilter);
    }

    @GetMapping("notOfInterest")
    public List<LegoSet> findUnpopular(){
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isFalse();
        Predicate rateFilter = query.reviews.any().isNull();
        Predicate unPopularProductsFilter = inStockFilter.or(rateFilter);
        return (List<LegoSet>) legoSetRepository.findAll(unPopularProductsFilter);
    }

    @GetMapping("avgRatings")
    public List<AvgRatingModel> findAllAvgRatings(){
        return legoSetRepository.findAllAverageRatings();
    }

    @GetMapping("findText/{text}")
    public List<LegoSet> findText(@PathVariable String text){
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return legoSetRepository.findAllBy(textCriteria);
    }

//    @GetMapping("/getByPaymentOption/{id}")
//    public List<LegoSet> getByPaymentOption(@PathVariable String id){
//      return legoSetRepository.getByPaymentOption(id);
//    }
}
