package org.example.taskmanager.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.taskmanager.core.domain.Task;
import org.example.taskmanager.core.exception.TaskNotFoundException;
import org.hibernate.SessionFactory;

import java.util.List;

public class TaskDAO extends AbstractDAO<Task> {
    public TaskDAO(SessionFactory factory) {
        super(factory);
    }

    public Task findTaskById(int id) {
        return get(id);
    }

    public Task createTask(Task task) {
        return persist(task);
    }

    public List<Task> findAllTasks() {
        return list(namedTypedQuery("Task.findAll"));
    }

    public void deleteTaskById(int id) {
        Task task = get(id);
        if (task == null)
            throw new TaskNotFoundException();

        this.currentSession().delete(get(id));
    }

    public void updateTask(Task task) {
        Task existingTask = get(task.getId());
        if (existingTask == null)
            throw new TaskNotFoundException();

        this.currentSession().merge(task);
    }
}
