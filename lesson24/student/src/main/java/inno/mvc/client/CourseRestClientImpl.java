package inno.mvc.client;

import inno.mvc.model.Course;
import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Component
public class CourseRestClientImpl implements CourseClient {

    private RestClient restClient;

    @PostConstruct
    private void init() {
        restClient = RestClient.builder().baseUrl("http://localhost:8081/").build();
    }

    @Override
    public Course findById(Integer id) {
        return restClient.get().uri("/course/" + id).accept(MediaType.APPLICATION_JSON).exchange((request, response) -> response.bodyTo(Course.class));
    }

    @Override
    public List<Course> findAll() {
        return Collections.singletonList(restClient.get().uri("/course").exchange(((request, response) -> response.bodyTo(Course.class))));
    }
}
