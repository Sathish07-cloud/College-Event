package com.eventease.controller;

import com.eventease.service.EventService;
import com.eventease.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Thymeleaf Controller for Home page
@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private StudentService studentService;

    // GET / - Show the home page
    @GetMapping("/")
    public String showHomePage(Model model) {
        // Show total event count on home page
        int totalEvents = eventService.getAllEvents().size();
        int totalStudents = studentService.getAllStudents().size();

        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("totalStudents", totalStudents);

        return "index";
    }
}
