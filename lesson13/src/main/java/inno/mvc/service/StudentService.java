package inno.mvc.service;

import inno.mvc.model.Student;
import inno.mvc.repository.studentImpl.StudentRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepositoryImpl studentRepository;

    public Student registrationStudent(Student student) {
        return studentRepository.create(student);
    }

    public String signupCourse(Integer id, String courseName) {
        studentRepository.updateCourse(id, courseName);
        return courseName;
    }
}
