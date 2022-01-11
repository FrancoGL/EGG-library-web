package com.alef.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView loginForm(@RequestParam(required = false) String error,
                                  @RequestParam(required = false) String logout,
                                  Principal principal, HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("sign-in");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if (map != null) {
            mav.addObject("success", map.get("success-register"));
        }

        if (error != null) {
            mav.addObject("error", "email or password invalid");
        }

        if (logout != null) {
            mav.addObject("logout", "Logout successful");
        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        return mav;
    }
}
