package innopolis.service;


import innopolis.client.CourseRestClientImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import innopolis.model.Course;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
public class CourseSchedule {
    private final CourseRestClientImpl courseRestClient;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void deactivateCourseAfterStart() {
        log.info("Я отработал");
        var listOfCourseId = courseRestClient.findAll().stream()
                .filter(course -> course.getStartDate().before(Timestamp.valueOf(LocalDateTime.now())))
                .map(Course::getId)
                .toList();

        if (!listOfCourseId.isEmpty()) {
            listOfCourseId.forEach(courseRestClient::deactivateCourse);
            log.info("Курс(ы) {} были переведены в архивных режим т.к. они уже стартовали", listOfCourseId);
        }

    }


}
