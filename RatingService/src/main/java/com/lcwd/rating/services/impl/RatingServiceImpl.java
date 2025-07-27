package com.lcwd.rating.services.impl;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.repositries.RatingRepositries;
import com.lcwd.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
     private RatingRepositries ratingRepositries;


    @Override
    public Rating create(Rating rating) {
        return ratingRepositries.save(rating);
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId) {
        return ratingRepositries.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingRepositries.findByHotelId(hotelId);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepositries.findAll();
    }

    @Override
    public Rating delete(String ratingId) {
        Rating rating = ratingRepositries.findById(ratingId).get();
        ratingRepositries.delete(rating);
        return rating;
    }
}
