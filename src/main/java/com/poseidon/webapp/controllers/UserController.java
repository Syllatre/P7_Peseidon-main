package com.poseidon.webapp.controllers;


import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.service.UserServiceImp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserServiceImp userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        log.info("Display user list");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        User user = new User();
        log.info("Display new user form");
        model.addAttribute("user", user);
        return "user/add";
    }

    @PostMapping("/user/add")
    public String validate(@Valid User user, BindingResult result) {
//        model.addAttribute("users", userService.findAll());
        if (result.hasErrors()) {
            return "user/add";
        }
        if (userService.existsByUserName(user.getUsername())) {
            log.info("Username existing");
            result.rejectValue("username", "UsernameExist", "Ce username est deja existant");
            return "user/add";
        }
        userService.create(user);
        log.info("Username " + user + " was saved");
        return "redirect:/";

    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        log.info("return form with " + user + " to update it");
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.update(user);
        model.addAttribute("users", userService.findAll());
        log.info("User " + user + " was updated");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.delete(user);
        model.addAttribute("users", userService.findAll());
        log.info("User " + id + " was deleted");
        return "redirect:/user/list";
    }
}
