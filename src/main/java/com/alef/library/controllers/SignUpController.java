package com.alef.library.controllers;

import com.alef.library.entities.UserEntity;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView signUpForm(Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("sign-up");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if (map != null) {
            mav.addObject("error", map.get("error-register"));
        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        mav.addObject("user", new UserEntity());
        mav.addObject("title", "Sign Up");
        mav.addObject("action", "save-user");
        return mav;
    }

    @PostMapping("/save-user")
    public RedirectView saveUser(@ModelAttribute UserEntity userEntity,
                                 RedirectAttributes attributes) {

        try {
            userService.createUser(userEntity);
            attributes.addFlashAttribute("success-register", "Success");
            return new RedirectView("/login");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-register", e.getMessage());
            return new RedirectView("/sign-up");
        }
    }
}
