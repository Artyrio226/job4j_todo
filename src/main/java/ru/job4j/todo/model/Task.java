package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    LocalDateTime created = LocalDateTime.now();
    boolean done;
}
