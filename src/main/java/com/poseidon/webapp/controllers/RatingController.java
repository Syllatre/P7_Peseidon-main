package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.service.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class RatingController {

    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        List<Rating> ratingList = ratingService.findAll();
        model.addAttribute("ratingList",ratingList);
        log.debug("Display bid List");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        Rating rating = new Rating();
        model.addAttribute("rating",rating);
        log.debug("return new form");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()){
            log.debug("informations is not valid");
            return "rating/add";
        }
        ratingService.create(rating);
        log.debug("rating " +rating+" was add");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id);
        model.addAttribute("rating",rating);
        log.debug("return form with "+rating+" to update it");
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            log.debug("informations is not valid");
            return "rating/update/{id}";
        }
        ratingService.update(rating);
        log.debug("Rating " +rating+" was updated");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.delete(id);
        log.debug("Rating " +id+" was deleted");
        return "redirect:/rating/list";
    }
}
