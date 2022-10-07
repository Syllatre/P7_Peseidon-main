package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.repositories.RatingRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;
    @Mock
    private RatingRepository ratingRepository;

    private Optional<Rating> rating;

    @BeforeEach
    private void init() {
        rating = Optional.of(new Rating("moodysRating", "sandPRating", "fitchRating", 1));

    }

    @AfterAll
    private void deletedBidList() {
        ratingRepository.delete(rating.get());
    }

    @Test
    void updateTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(rating);
        Rating updateRating = rating.get();
        updateRating.setOrderNumber(20);
        when(ratingRepository.save(updateRating)).thenReturn(updateRating);

        ratingService.updateRating(1, rating.get());
        assertEquals(updateRating.getOrderNumber(), 20);
        assertTrue(ratingService.updateRating(1, rating.get()));
    }

    @Test
    void updateFailTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());
        Rating updateRating = rating.get();
        updateRating.setOrderNumber(18);

        ratingService.updateRating(1, rating.get());
        assertFalse(ratingService.updateRating(1, rating.get()));
    }

    @Test
    void createTest() {
        when(ratingRepository.save(rating.get())).thenReturn(rating.get());
        ratingService.create(rating.get());

        verify(ratingRepository, times(1)).save(rating.get());
    }

    @Test
    void deleteTest() {
        Rating rating1 = rating.get();
        rating1.setId(1);
        ratingService.delete(rating1.getId());

        verify(ratingRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<Rating> curvePointArrayList = new ArrayList<>();
        curvePointArrayList.add(rating.get());
        when(ratingRepository.findAll()).thenReturn(curvePointArrayList);
        assertEquals(ratingRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(rating);
        Rating rating1 = ratingService.findById(1);
        assertEquals(rating1.getOrderNumber(), 1);
    }
}
