package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.service.CurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/curvePoint")
@Slf4j
public class CurveController {
    private CurveService curveService;

    @RequestMapping("/list")
    public String home(Model model)
    {
        List<CurvePoint> curveList = curveService.findAll();
        model.addAttribute("curveList",curveList);
        log.debug("Display curvePoint List");
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        CurvePoint curvePoint = new CurvePoint();
        model.addAttribute("curvePoint", curvePoint);
        log.debug("return new form");
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()){
            log.debug("informations is not valid");
            return "curvePoint/add";
        }
        curveService.create(curvePoint);
        log.debug("CurvePoint " +curvePoint+" was add");
        return "curvePoint/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.findById(id);
        model.addAttribute("curvePoint",curvePoint);
        log.debug("return form with "+curvePoint+" to update it");
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            log.debug("informations is not valid");
            return "curvePoint/update/{id}";
        }
        curveService.update(curvePoint);
        log.debug("CurvePoint " +curvePoint+" was updated");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curveService.delete(id);
        log.debug("curvePoint " +id+" was deleted");
        return "redirect:/curvePoint/list";
    }
}
