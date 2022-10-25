package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.service.TradeService;
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
@Slf4j
@AllArgsConstructor
public class TradeController {
    TradeService tradeService;

    @GetMapping("/trade/list")
    public String home(Model model) {
        List<Trade> tradeList = tradeService.findAll();
        model.addAttribute("tradeList", tradeList);
        log.info("Display bid List");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        Trade trade = new Trade();
        model.addAttribute("trade", trade);
        log.info("return new form");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.create(trade);
        log.info("trade " + trade + " was add");
        model.addAttribute("tradeList", tradeService.findAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        log.info("return form with " + trade + " to update it");
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "trade/update";
        }
        Boolean updated = tradeService.updateTrade(id, trade);
        if (updated) {
            model.addAttribute("trade", tradeService.findAll());
            log.info("Trade " + trade + " was updated");
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);
        log.info("trade " + id + " was deleted");
        return "redirect:/trade/list";
    }
}
