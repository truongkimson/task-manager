package org.example.taskmanager.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@NamedQueries({
        @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t ORDER BY t.date"),
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String description;

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Transient
    boolean overdue;

    public Task() {
    }

    public Task(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonProperty
    public boolean isOverdue() {
        return this.date.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", date=" + date +
               ", overdue=" + overdue +
               '}';
    }
}
