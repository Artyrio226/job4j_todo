package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.service.task.SimpleTaskService;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "index"})
public class IndexController {
    private final SimpleTaskService simpleTaskService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", simpleTaskService.findAll());
        return "index";
    }

    @GetMapping("/completed")
    public String getAllCompleted(Model model, @RequestParam("done") boolean done) {
        model.addAttribute("tasks", simpleTaskService.findByDone(done));
        return "/completed";
    }

    @GetMapping("/new")
    public String getAllNew(Model model, @RequestParam("done") boolean done) {
        model.addAttribute("tasks", simpleTaskService.findByDone(done));
        return "/new";
    }
}
