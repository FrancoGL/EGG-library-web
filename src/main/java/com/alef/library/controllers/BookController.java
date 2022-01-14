package com.alef.library.controllers;

import com.alef.library.entities.BookEntity;
import com.alef.library.services.AuthorService;
import com.alef.library.services.BookService;
import com.alef.library.services.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final EditorialService editorialService;

    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService,
                          EditorialService editorialService,
                          AuthorService authorService) {
        this.bookService = bookService;
        this.editorialService = editorialService;
        this.authorService = authorService;
    }

    // ** Home ** //

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ModelAndView booksHome(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("books-home");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null) {
            mav.addObject("success", map.get("success"));
            mav.addObject("error", map.get("error"));
        }

        mav.addObject("books", bookService.getAllBooks());

        return mav;
    }

    // ** Save ** //

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createBook(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("books-form");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null) {
            mav.addObject("error", map.get("error"));
        }

        mav.addObject("book", new BookEntity());
        mav.addObject("authors", authorService.getAllAuthors());
        mav.addObject("editorials", editorialService.getAllEditorials());
        mav.addObject("title", "Add New Book");
        mav.addObject("action", "create");

        return mav;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView createBook(@ModelAttribute BookEntity book,
                                   RedirectAttributes attributes) {

        try {
            bookService.saveBook(book);
            attributes.addFlashAttribute("success", "Book added");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error: " + ex.getMessage()));
            return new RedirectView("/books/create");
        }
        return new RedirectView("/books");
    }

    // ** Update ** //

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateBook(@PathVariable String id) {

        ModelAndView mav = new ModelAndView("books-form");

        try {
            mav.addObject("book", bookService.getBookById(id));
            mav.addObject("title","Update Books");
            mav.addObject("authors", authorService.getAllAuthors());
            mav.addObject("editorials", editorialService.getAllEditorials());
            mav.addObject("action","edit");
        } catch (Exception ex) {
            mav.addObject("error", ("Error " + ex.getMessage()));
        }

        return mav;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView updateBook(@RequestParam String id,
                                   @ModelAttribute BookEntity book,
                                   RedirectAttributes attributes) {

        try {
            bookService.updateBook(id, book);
            attributes.addFlashAttribute("success","Book updated");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error: " + ex.getMessage()));
        }

        return new RedirectView("/books");
    }

    // ** Delete ** //

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RedirectView deleteBook(@PathVariable String id,
                                   RedirectAttributes attributes) {

        try {
            bookService.deleteBook(id);
            attributes.addFlashAttribute("success", "Book deleted");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ("Error " + ex.getMessage()));
        }
        return new RedirectView("/books/");
    }
}
