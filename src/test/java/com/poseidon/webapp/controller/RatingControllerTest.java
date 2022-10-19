package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationTest;
import com.poseidon.webapp.controllers.RatingController;
import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
@Import(ConfigurationTest.class)
public class RatingControllerTest {

    private Rating rating1;

    private Rating rating2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        rating1 = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        rating2 = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        rating1.setId(1);
        rating2.setId(2);
    }

    @Test
    @WithUserDetails("aimen")
    public void displayRatingList() throws Exception {
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating1, rating2));
        mockMvc.perform(get("/rating/list"))
                .andExpect(model().attributeExists("ratingList"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());

        verify(ratingService).findAll();
    }

    @Test
    @WithUserDetails("aimen")
    public void displayAddForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("aimen")
    void addValideRating() throws Exception {
        when(ratingService.create(rating1)).thenReturn(rating1);
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating2));

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", rating1.getSandPRating())
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", rating1.getOrderNumber().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).create(any(Rating.class));
    }

    @Test
    @WithUserDetails("aimen")
    void addValideRatingWithError() throws Exception {
        when(ratingService.create(rating1)).thenReturn(rating1);
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating2));

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", "")
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", rating1.getOrderNumber().toString())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"))
                .andReturn();

        verify(ratingService, times(0)).create(any(Rating.class));
    }

    @Test
    @WithUserDetails("aimen")
    public void showUpdateFormRating() throws Exception {
        when(ratingService.findById(1)).thenReturn(rating1);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk());

        verify(ratingService).findById(1);
    }

    @Test
    @WithUserDetails("aimen")
    void updateCurvePoint() throws Exception {
        when(ratingService.updateRating(1, rating1)).thenReturn(true);

        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", rating1.getOrderNumber().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).updateRating(anyInt(), any(Rating.class));
    }

    @Test
    @WithUserDetails("aimen")
    void wrongUpdateCurvePoint() throws Exception {
        when(ratingService.updateRating(1, rating1)).thenReturn(true);

        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", "")
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", rating1.getOrderNumber().toString())
                        .with(csrf()))
                .andExpect(view().name("rating/update"))
                .andReturn();

        verify(ratingService, times(0)).updateRating(anyInt(), any(Rating.class));
    }

    @Test
    @WithUserDetails("aimen")
    void deleteCurvePoint() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).delete(1);
    }
}
