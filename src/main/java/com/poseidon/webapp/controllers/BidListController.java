package com.poseidon.webapp.controllers;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.service.BidListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
@Slf4j
public class BidListController {
    private BidListService bidListService;


    @GetMapping("/bidList/list")
    public String home(Model model) {
        List<BidList> bidLists = bidListService.findAll();
        model.addAttribute("bidLists", bidLists);
        log.debug("Display bid List");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        BidList bidList = new BidList();
        model.addAttribute("bidList", bidList);
        log.debug("return new form");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "bidList/add";
        }
        bidListService.create(bidList);
        log.debug("Bid " + bidList + " was add");
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{BidListId}")
    public String showUpdateForm(@PathVariable("BidListId") Integer BidListId, Model model) {
        BidList bidList = bidListService.findById(BidListId);
        model.addAttribute("bidList", bidList);
        log.debug("return form with " + bidList + " to update it");
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{BidListId}")
    public String updateBid(@PathVariable("BidListId") Integer BidListId, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("informations is not valid");
            return "bidList/update";
        }
        Boolean updated = bidListService.updateBidList(BidListId, bidList);
        if (updated) {
            model.addAttribute("bidLists", bidListService.findAll());
            log.debug("Bid " + bidList + " was updated");
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{bidListId}")
    public String deleteBid(@PathVariable("bidListId") Integer id, Model model) {
        bidListService.delete(id);
        log.debug("Bid " + id + " was deleted");
        return "redirect:/bidList/list";
    }
}
