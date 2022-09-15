package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {

    private RatingRepository ratingRepository;

    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }

    public Rating create (Rating rating){
        return ratingRepository.save(rating);
    }

    public Rating update (Rating rating){
        return ratingRepository.save(rating);
    }

    public void delete(int id){
        ratingRepository.deleteById(id);
    }

    public Rating findById(int id){
        return ratingRepository.findById(id).get();
    }
}
