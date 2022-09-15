package com.poseidon.webapp.controllers;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.service.BidListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/bidList")
@Slf4j
public class BidListController {
    private BidListService bidListService;

    @GetMapping("/list")
    public String home(Model model) {
        List<BidList> bidLists = bidListService.findAll();
        model.addAttribute("bidLists", bidLists);
        log.debug("Display bid List");
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        BidList bidList = new BidList();
        model.addAttribute("bidList", bidList);
        log.debug("return new form");
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidList bidList, BindingResult result) {
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "bidList/add";
        }
        bidListService.create(bidList);
        log.debug("Bid " +bidList+" was add");
        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.findById(id);
        model.addAttribute("bidList", bidList);
        log.debug("return form with "+bidList+" to update it");
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "/update/{id}";
        }
        bidListService.update(bidList);
        log.debug("Bid " +bidList+" was updated");
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);
        log.debug("Bid " +id+" was deleted");
        return "redirect:/bidList/list";
    }
}
