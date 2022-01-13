package com.alef.library.controllers;

import com.alef.library.entities.UserEntity;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView profileHome(HttpSession session) {

        ModelAndView mav = new ModelAndView("profiles-home");

        mav.addObject("user", userService.getUserById(session.getAttribute("id").toString()));
        mav.addObject("title", "Profile");
        mav.addObject("loans", userService.getLoans(session.getAttribute("id").toString()));

        return mav;
    }
}
