package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RatingService {

    private RatingRepository ratingRepository;

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void delete(int id) {
        ratingRepository.deleteById(id);
    }

    public Rating findById(int id) {
        return ratingRepository.findById(id).get();
    }

    public Boolean updateRating(int id, Rating rating) {
        Optional<Rating> ratingExist = ratingRepository.findById(id);
        if (ratingExist.isPresent()) {
            Rating updateRating = ratingExist.get();
            updateRating.setMoodysRating(rating.getMoodysRating());
            updateRating.setSandPRating(rating.getSandPRating());
            updateRating.setFitchRating(rating.getFitchRating());
            updateRating.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(updateRating);
            log.info("Rating with id " + id + " is updated as " + updateRating);
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;
    }
}
