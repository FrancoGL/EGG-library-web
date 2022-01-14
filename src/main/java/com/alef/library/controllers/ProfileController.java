package com.alef.library.controllers;

import com.alef.library.entities.UserEntity;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // ** Home ** //

    @GetMapping
    public ModelAndView profileHome(HttpSession session) {

        ModelAndView mav = new ModelAndView("profiles-home");

        mav.addObject("user", userService.getUserById(session.getAttribute("id").toString()));
        mav.addObject("title", "Profile");
        mav.addObject("loans", userService.getLoans(session.getAttribute("id").toString()));

        return mav;
    }

    // ** Update ** //

    @GetMapping("/edit/{id}")
    public ModelAndView updateUser(@PathVariable String id) {

        ModelAndView mav = new ModelAndView("profiles-form");

        try {
            mav.addObject("user", userService.getUserById(id));
            mav.addObject("title", "Update User");
            mav.addObject("action","edit");
        } catch (Exception ex) {
            mav.addObject("error", ("Error: " + ex.getMessage()));
        }

        return mav;
    }

    @PostMapping("/edit")
    public RedirectView updateUser(@RequestParam String id,
                                   @ModelAttribute UserEntity user,
                                   RedirectAttributes attributes) {

        try {
            userService.updateUser(id,user);
            attributes.addFlashAttribute("success","Updated successful");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error",("Error: " + ex.getMessage()));
        }

        return new RedirectView("/profiles");
    }

    // ** Delete ** //

    @PostMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable String id,
                                   RedirectAttributes attributes) {

        try {
            userService.deleteUser(id);
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error: " +  ex.getMessage()));
        }

        return new RedirectView("/logout");
    }
}
