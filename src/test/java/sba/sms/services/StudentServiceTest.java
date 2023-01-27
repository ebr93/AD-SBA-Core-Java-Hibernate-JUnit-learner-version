package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;
    static CourseService courseService;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        courseService = new CourseService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));

        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

    }
    
    @Test
    void getAllCourses() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course("Java", "Phillip Witkin"),
                new Course("Frontend", "Kasper Kain"),
                new Course("JPA", "Jafer Alhaboubi"),
                new Course("Spring Framework", "Phillip Witkin"),
                new Course("SQL", "Phillip Witkin")
        ));
        
        for (int i = 0; i < expected.size(); i++) {
        	int num = i + 1;
        	expected.get(i).setId(num);
        }

        assertThat(courseService.getAllCourses()).hasSameElementsAs(expected);

    }
    
    /*
    @Test
    void getStudentByEmail() {

        Student expected = new Student("annette@gmail.com", "annette allen", "password");

        assertThat(studentService.getStudentByEmail("annette@gmail.com")).returns(expected, null)

    }
    */
}