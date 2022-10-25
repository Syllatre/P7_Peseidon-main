package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.service.RuleNameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@Slf4j
public class RuleNameController {
    RuleNameService ruleNameService;

    @GetMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleName> ruleNameList = ruleNameService.findAll();
        model.addAttribute("ruleNameList", ruleNameList);
        log.info("Display bid List");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        RuleName ruleName = new RuleName();
        model.addAttribute("ruleName", ruleName);
        log.info("return new form");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "ruleName/add";
        }
        ruleNameService.create(ruleName);
        log.info("ruleName " + ruleName + " was add");
        model.addAttribute("ruleNameList", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleName);
        log.info("return form with " + ruleName + " to update it");
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "ruleName/update";
        }
        Boolean updated = ruleNameService.updateRuleName(id, ruleName);
        if (updated) {
            model.addAttribute("rating", ruleNameService.findAll());
            log.info("ruleName " + ruleName + " was updated");
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        log.info("ruleName " + id + " was deleted");
        return "redirect:/ruleName/list";
    }
}
