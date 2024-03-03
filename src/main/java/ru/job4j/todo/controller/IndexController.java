package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.task.SimpleTaskService;

import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "index"})
public class IndexController {
    private final SimpleTaskService simpleTaskService;

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String zoneId = user == null ? TimeZone.getDefault().getID() : user.getTimezone();
        model.addAttribute("tasks", getTimeZonedTasks(simpleTaskService.findAll(), zoneId));
        return "index";
    }

    @GetMapping("/completed")
    public String getAllCompleted(Model model, @RequestParam("done") boolean done, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String zoneId = user == null ? TimeZone.getDefault().getID() : user.getTimezone();
        model.addAttribute("tasks", getTimeZonedTasks(simpleTaskService.findByDone(done), zoneId));
        return "/completed";
    }

    @GetMapping("/new")
    public String getAllNew(Model model, @RequestParam("done") boolean done, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String zoneId = user == null ? TimeZone.getDefault().getID() : user.getTimezone();
        model.addAttribute("tasks", getTimeZonedTasks(simpleTaskService.findByDone(done), zoneId));
        return "/new";
    }

    private List<Task> getTimeZonedTasks(Collection<Task> tasks, String zoneId) {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            task.setCreated(task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(zoneId, ZoneId.SHORT_IDS))
                    .toLocalDateTime());
            list.add(task);
        }
        return list;
    }
}
