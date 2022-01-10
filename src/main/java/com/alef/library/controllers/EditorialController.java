package com.alef.library.controllers;

import com.alef.library.entities.EditorialEntity;
import com.alef.library.services.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/editorials")
public class EditorialController {

    private final EditorialService editorialService;

    @Autowired
    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    // ** Home ** //
    @GetMapping
    public ModelAndView editorialsHome(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("editorials-home");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null) {
            mav.addObject("success", map.get("success"));
            mav.addObject("error", map.get("error"));
        }

        mav.addObject("editorials", editorialService.getAllEditorials());

        return mav;
    }

    // ** Create ** //

    @GetMapping("/create")
    public ModelAndView createEditorial(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("editorials-form");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null) {
            mav.addObject("error", map.get("error"));
        }

        mav.addObject("editorial", new EditorialEntity());
        mav.addObject("title", "Add New Editorial");
        mav.addObject("action","create");

        return mav;
    }

    @PostMapping("/create")
    public RedirectView createEditorial(@ModelAttribute EditorialEntity editorial,
                                        RedirectAttributes attributes) {

        try {
            editorialService.saveEditorial(editorial);
            attributes.addFlashAttribute("success","Editorial created");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error",("Error: " + ex.getMessage()));
            return new RedirectView("/editorials/create");
        }

        return new RedirectView("/editorials");
    }

    // ** Update ** //

    @GetMapping("/edit/{id}")
    public ModelAndView editEditorial(@PathVariable String id) {

        ModelAndView mav = new ModelAndView("editorials-form");

        try {
            mav.addObject("editorial", editorialService.getEditorialById(id));
            mav.addObject("title", "Update Editorial");
            mav.addObject("action","edit");
        } catch (Exception ex) {
            mav.addObject("error", ("Error: " + ex.getMessage()));
        }

        return mav;
    }

    @PostMapping("/edit")
    public RedirectView editEditorial(@RequestParam String id,
                                      @ModelAttribute EditorialEntity editorial,
                                      RedirectAttributes attributes) {

        try {
            editorialService.updateEditorial(id, editorial);
            attributes.addFlashAttribute("success", "Editorial update");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error",("Error: " + ex.getMessage()));
        }

        return new RedirectView("/editorials");
    }

    // ** Deleted ** //

    @PostMapping("/delete/{id}")
    public RedirectView deleteEditorial(@PathVariable String id,
                                        RedirectAttributes attributes) {

        try {
            editorialService.deleteEditorial(id);
        } catch (Exception ex) {
            attributes.addFlashAttribute("error",("Error: " + ex.getMessage()));
        }

        return new RedirectView("/editorials");
    }
}
