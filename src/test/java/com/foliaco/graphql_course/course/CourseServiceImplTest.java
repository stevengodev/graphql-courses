package com.foliaco.graphql_course.course;

import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.CourseInput;
import com.foliaco.graphql_course.lesson.Lesson;
import com.foliaco.graphql_course.lesson.LessonRepository;
import com.foliaco.graphql_course.resource.Resource;
import com.foliaco.graphql_course.resource.ResourceType;
import com.foliaco.graphql_course.user.User;
import com.foliaco.graphql_course.user.UserRepository;
import com.foliaco.graphql_course.util.DataPaginator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    private User teacher;

    @BeforeEach
    void setUp() {
        teacher = new User();
        teacher.setId(1);
        teacher.setName("Teacher");

        course = new Course();
        course.setId(1);
        course.setTitle("Course Fullstack");
        course.setDescription("Course Description");
        course.setLessons(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setTeacher(teacher);
    }

    @Test
    void givenCourseId_whenFindById_thenReturnCourseWithLessons() {

        Integer courseId = 1;

        Lesson lesson = new Lesson();
        lesson.setId(10);
        lesson.setTitle("Lesson");
        lesson.setContent("Content");

        Resource resource = new Resource(1, ResourceType.PDF, "http://localhost:8080/pdf", "pdf", lesson);
        lesson.setResources(List.of(resource));

        course.getLessons().add(lesson);

        when(courseRepository.findCourseById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.findByCourseId(courseId)).thenReturn(List.of(lesson));

        Course courseFound = courseService.findById(1);

        assertNotNull(courseFound);
        assertEquals(1, courseFound.getLessons().size());
    }

    @Test
    void givenIdNotExits_whenFindById_thenThrowException() {
        Integer idNotExists = 2;
        when(courseRepository.findCourseById(idNotExists)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courseService.findById(idNotExists));

    }

    @Test
    void whenFindAllCourses_thenReturnCoursesPaginated() {
        List<Course> courses = List.of(course);
        Page<Course> page = new PageImpl<>(courses, PageRequest.of(0, 10), 1);
        when(courseRepository.findAll(any(PageRequest.class))).thenReturn(page);

        DataPaginator<List<Course>> paginator = courseService.findAll(PageRequest.of(0, 10));

        assertEquals(1, paginator.getTotalPages());
        assertEquals(0, paginator.getCurrentPage());
    }

    @Test
    void givenCourseInput_whenCreateCourse_thenSaveCourse() {
        CourseInput input = new CourseInput("Course Fullstack", "Course Description", 1);

        when(userRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(courseMapper.toCourse(input, teacher)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        Course savedUser = courseService.createCourse(input);

        assertEquals("Course Fullstack", savedUser.getTitle());
        verify(courseRepository).save(course);
    }

    @Test
    void givenId_whenDeleteById_thenRemoveStudentsAndDeleteCourse() {
        course.getStudents().add(new User());
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        boolean isDeleted = courseService.deleteById(1);

        assertTrue(isDeleted);
        assertTrue(course.getStudents().isEmpty());
        verify(courseRepository).delete(course);
    }

    @Test
    void givenStudentAndCourse_whenEnrollStudentToCourse_thenAddStudentToCourse() {
        User student = new User();
        student.setId(2);

        when(courseRepository.findCourseById(1)).thenReturn(Optional.of(course));
        when(userRepository.findById(2)).thenReturn(Optional.of(student));
        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseService.enrollStudentToCourse(2, 1);

        assertNotNull(savedCourse);
        assertTrue(savedCourse.getStudents().contains(student));
    }

    @Test
    void givenIdAndCourse_whenUpdateCourse_thenUpdateCourse() {
        CourseInput input = new CourseInput("Updated title", "Updated description", 1);
        when(courseRepository.findCourseById(1)).thenReturn(Optional.of(course));
        when(userRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any())).thenReturn(course);

        Course updatedCourse = courseService.updateCourse(1, input);

        assertEquals("Updated title", updatedCourse.getTitle());
        assertEquals("Updated description", updatedCourse.getDescription());
        verify(courseRepository).save(course);
    }

}
