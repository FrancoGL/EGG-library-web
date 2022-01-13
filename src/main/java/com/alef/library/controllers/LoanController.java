package com.alef.library.controllers;

import com.alef.library.entities.BookEntity;
import com.alef.library.entities.LoanEntity;
import com.alef.library.services.BookService;
import com.alef.library.services.LoanService;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    private final BookService bookService;

    private final UserService userService;

    @Autowired
    public LoanController(LoanService loanService, BookService bookService, UserService userService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/create/{id}")
    public ModelAndView confirmPage(@PathVariable String id) {

        ModelAndView mav = new ModelAndView("loans-home");

        try {
            BookEntity entity = bookService.getBookById(id);

            mav.addObject("book", Objects.requireNonNullElseGet(entity, BookEntity::new));
            mav.addObject("title","Loan");
            mav.addObject("action", "create");
        } catch (Exception ex) {
            mav.addObject("error", ex.getMessage());
        }
        return mav;
    }

    @PostMapping("/create")
    public RedirectView confirmLoan(@RequestParam String id,
                                    RedirectAttributes attributes,
                                    HttpSession session) {

        try {
            loanService.createLoan(id, session);
            return new RedirectView("/books");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
            return new RedirectView("/books");
        }
    }

    @PostMapping("/refund/{id}")
    public RedirectView refundBook(@PathVariable String id,
                                   RedirectAttributes attributes) {

        try {
            loanService.updateLoan(id);
            attributes.addFlashAttribute("success", "Refund!");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
        }

        return new RedirectView("/profiles");
    }
}
