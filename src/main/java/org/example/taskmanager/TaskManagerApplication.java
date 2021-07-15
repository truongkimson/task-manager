package org.example.taskmanager;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.taskmanager.core.domain.Task;
import org.example.taskmanager.core.filter.CorsFilter;
import org.example.taskmanager.db.TaskDAO;
import org.example.taskmanager.resources.TaskResource;

public class TaskManagerApplication extends Application<TaskManagerConfiguration> {
    private final HibernateBundle<TaskManagerConfiguration> hibernate = new HibernateBundle<TaskManagerConfiguration>(
            Task.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                TaskManagerConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new TaskManagerApplication().run(args);
    }

    @Override
    public String getName() {
        return "TaskManager";
    }

    @Override
    public void initialize(final Bootstrap<TaskManagerConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<TaskManagerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TaskManagerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final TaskManagerConfiguration configuration,
                    final Environment environment) {
        final TaskDAO taskDAO = new TaskDAO(hibernate.getSessionFactory());
        environment.jersey().register(new TaskResource(taskDAO));

        final CorsFilter corsFilter = new CorsFilter();
        environment.jersey().register(corsFilter);

        // display Json serialization exceptions in response, for debugging
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

}
