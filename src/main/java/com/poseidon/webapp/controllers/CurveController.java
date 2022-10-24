package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.service.CurveService;
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
public class CurveController {
    private CurveService curveService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePoint> curveList = curveService.findAll();
        model.addAttribute("curveList", curveList);
        log.debug("Display curvePoint List");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        CurvePoint curvePoint = new CurvePoint();
        model.addAttribute("curvePoint", curvePoint);
        log.debug("return new form");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        model.addAttribute("curveList", curveService.findAll());
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "curvePoint/add";
        }
        curveService.create(curvePoint);
        log.debug("CurvePoint " + curvePoint + " was add");


        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        log.debug("return form with " + curvePoint + " to update it");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "curvePoint/update";
        }
        Boolean updated = curveService.updateCurvePoint(id, curvePoint);
        if (updated) {
            model.addAttribute("bidLists", curveService.findAll());
            log.debug("CurvePoint " + curvePoint + " was updated");
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curveService.delete(id);
        log.debug("curvePoint " + id + " was deleted");
        return "redirect:/curvePoint/list";
    }
}
