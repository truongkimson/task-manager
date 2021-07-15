package org.example.taskmanager.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.example.taskmanager.core.domain.Task;
import org.example.taskmanager.core.exception.TaskNotFoundException;
import org.example.taskmanager.db.TaskDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Console;
import java.util.List;

@Path("/api/v1/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GET
    @Path("/all")
    @Timed
    @UnitOfWork
    public List<Task> getAllTasks() {
        return taskDAO.findAllTasks();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Task getTask(@PathParam("id") int id) {
        return taskDAO.findTaskById(id);
    }

    @POST
    @Path("/create")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(@Valid Task task) {
        System.out.println(task.toString());
        Task createdTask = taskDAO.createTask(task);
        return Response.status(Response.Status.CREATED).entity(createdTask).build();
        // return 422 if Task fields are invalid, return 400 if unable to parse request json body
    }

    @DELETE
    @Path("/{id}/delete")
    @Timed
    @UnitOfWork
    public Response deleteTask(@PathParam("id") int id) {
        try {
            taskDAO.deleteTaskById(id);
        } catch (TaskNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/update")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@Valid Task task) {
        try {
            taskDAO.updateTask(task);
        } catch (TaskNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.status(Response.Status.OK).entity(task).build();
        // return 422 if Task fields are invalid, return 400 if unable to parse request json body
    }
}
