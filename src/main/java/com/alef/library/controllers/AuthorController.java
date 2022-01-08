package com.alef.library.controllers;

import com.alef.library.entities.AuthorEntity;
import com.alef.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService aService;

    @Autowired
    public AuthorController(AuthorService aService) {
        this.aService = aService;
    }



    // ** Home ** //

    @GetMapping
    public ModelAndView authorsHome() {

        ModelAndView mav = new ModelAndView("authors-home");

        mav.addObject("authors", aService.getAllAuthors());

        return mav;
    }

    // ** Save ** //

    @GetMapping("/create")
    public ModelAndView createAuthor(@ModelAttribute AuthorEntity author) {

        ModelAndView mav = new ModelAndView("authors-form");

        mav.addObject("author", new AuthorEntity());
        mav.addObject("title", "Add Author");
        mav.addObject("action","create");

        return mav;
    }

    @PostMapping("/create")
    public RedirectView createEditorial(@ModelAttribute AuthorEntity author,
                                        RedirectAttributes attributes) {

        try {
            aService.saveAuthor(author);
            attributes.addFlashAttribute("success", "Author created");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error " + ex.getMessage()));
        }

        return new RedirectView("/authors");
    }

    // ** Update ** //

    @GetMapping("/edit/{id}")
    public ModelAndView editAuthor(@PathVariable String id) {

        ModelAndView mav = new ModelAndView("authors-form");

        try {
            mav.addObject("author", aService.getAuthorById(id));
            mav.addObject("title", "Update Author");
            mav.addObject("action","edit");
        } catch (Exception ex) {
            mav.addObject("error", ("Error " + ex.getMessage()));
        }

        return mav;
    }

    @PostMapping("/edit")
    public RedirectView editAuthor(@RequestParam String id,
                                   @ModelAttribute AuthorEntity author,
                                   RedirectAttributes attributes) {

        try {
            aService.updateAuthor(id, author);
            attributes.addFlashAttribute("success", "Author updated");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error " + ex.getMessage()));
        }

        return new RedirectView("/authors");
    }

    // ** Delete ** //

    @PostMapping("/delete/{id}")
    public RedirectView deleteAuthor(@PathVariable String id,
                                     RedirectAttributes attributes) {

        try {
            aService.deleteAuthor(id);
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error " +  ex.getMessage()));
        }

        return new RedirectView("/authors");
    }
}
