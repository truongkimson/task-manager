package org.example.taskmanager.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

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
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Transient
    private boolean overdue;

    public Task() {
    }

    public Task(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && description.equals(task.description) && date.equals(task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, date);
    }
}
