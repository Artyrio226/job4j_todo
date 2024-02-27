package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    int id;
    String title;
    String description;
    String created;
    boolean done;
}
