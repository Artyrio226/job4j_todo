package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd MMMM yyyy HH:mm");
    private final TaskStore taskStore;

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public Collection<TaskDto> findAll() {
        List<TaskDto> list = new ArrayList<>();
        var tasks = taskStore.findAll();
        for (Task task : tasks) {
            var sessionDto = getTaskDto(task);
            list.add(sessionDto);
        }
        return list;
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    private TaskDto getTaskDto(Task task) {
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                FORMATTER.format(task.getCreated()),
                task.isDone());
    }
}
