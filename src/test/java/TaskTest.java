import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.example.taskmanager.core.domain.Task;
import org.junit.Test;

import java.time.LocalDate;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskTest {
    private static final ObjectMapper Mapper = Jackson.newObjectMapper();

    @Test
    public void shouldDeserializeToObject() throws JsonProcessingException {
        final Task task = new Task("Learn Java", LocalDate.of(2021, 7, 1));

        assertThat(Mapper.readValue(fixture("fixtures/task.json"), Task.class)).isEqualTo(task);
    }

    @Test
    public void shouldSerializeToJSON() throws JsonProcessingException {
        final Task task = new Task("Learn Java", LocalDate.of(2021, 7, 1));

        String expected = Mapper.writeValueAsString(Mapper.readValue(fixture("fixtures/task.json"), Task.class));

        assertThat(Mapper.writeValueAsString(task)).isEqualTo(expected);
    }

    @Test
    public void shouldHaveOverdueValueTrue() {
        final Task task = new Task("Learn Java", LocalDate.of(2000, 1, 1));

        assertThat(task.isOverdue()).isEqualTo(true);
    }
}
