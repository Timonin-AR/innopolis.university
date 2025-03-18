package innopolis.client;

import innopolis.model.Course;
import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Component
public class CourseRestClientImpl implements CourseClient {

    private RestClient restClient;

    @PostConstruct
    private void init() {
        restClient = RestClient.builder().baseUrl("http://localhost:8080/").build();
    }

    @Override
    public Course findById(Integer id) {
        return restClient.get().uri("/course/" + id).accept(MediaType.APPLICATION_JSON).exchange((request, response) -> response.bodyTo(Course.class));
    }

    @Override
    public List<Course> findAll() {
       return List.of(Objects.requireNonNull(restClient.get().uri("/course").retrieve().body(Course[].class)));

    }

    @Override
    public void deactivateCourse(Integer id) {
        restClient.post().uri("/deactivate/" + id).exchange((req, res) -> res.getStatusCode());
    }
}
