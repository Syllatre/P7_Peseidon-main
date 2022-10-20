package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.service.RatingService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/poseidontest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RatingIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingService ratingService;

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    public void displayRatingList() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(model().attributeExists("ratingList"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void addValideRating() throws Exception {
        Rating rating1 = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", rating1.getSandPRating())
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", rating1.getOrderNumber().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        Rating ratingSaved = ratingService.findById(1);

        assertEquals(ratingSaved.getMoodysRating(), "moodysRating");

        ratingService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void updateCurvePoint() throws Exception {
        Rating rating1 = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        ratingService.create(rating1);

        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", rating1.getMoodysRating())
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", rating1.getFitchRating())
                        .param("orderNumber", "10")
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/rating/list"));
        Rating ratingSaved = ratingService.findById(1);
        assertEquals(ratingSaved.getOrderNumber(), 10);

        ratingService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void deleteCurvePoint() throws Exception {
        Rating rating1 = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        ratingService.create(rating1);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(redirectedUrl("/rating/list"));
    }
}
