package com.foliaco.graphql_course.course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.CourseInput;
import com.foliaco.graphql_course.lesson.Lesson;
import com.foliaco.graphql_course.lesson.LessonRepository;
import com.foliaco.graphql_course.user.User;
import com.foliaco.graphql_course.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private LessonRepository lessonRepository;

    private UserRepository userRepository;

    private CourseMapper courseMapper;

    @Transactional(readOnly = true)
    @Override
    public Course findById(Integer id) {
        Course course = courseRepository.findCourseById(id).orElseThrow(
            () -> new NotFoundException("Course not found with id: " + id )
        );

        List<Lesson> lessons = lessonRepository.findByCourseId(id);

        Map<Integer, Lesson> lessonsMap = lessons.stream()
                .collect(Collectors.toMap(Lesson::getId, lesson -> lesson));

        course.getLessons().forEach(lesson -> {
            Lesson fullLesson = lessonsMap.get(lesson.getId());
            if (fullLesson != null) {
                lesson.setResources(fullLesson.getResources());
            }
        });

        return course;
    }

    @Override
    public DataPaginator<List<Course>> findAll(Pageable pageable) {
        Page<Course> pageCourse = courseRepository.findAll(pageable);
        return new DataPaginator<>(pageCourse.getContent(), pageCourse.getTotalPages(), pageCourse.getNumber());
    }

    @Override
    public Course createCourse(CourseInput courseInput) {

        User user = userRepository.findById(courseInput.teacherId()).orElseThrow(
                () -> new NotFoundException("User not found with id: " + courseInput.teacherId() )
        );

        Course course = courseMapper.toCourse(courseInput, user);

        return courseRepository.save(course);
    }

    @Override
    public boolean deleteById(Integer id) {

        Course course = courseRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Course not found with id: " + id )
        );

        courseRepository.delete(course);
        return true;
    }

    @Override
    public Course enrollStudentToCourse(Integer studentId, Integer courseId) {

        Course course = courseRepository.findCourseById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found with id: " + courseId )
        );

        User user = userRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("User not found with id: " + studentId )
        );

        course.getStudents().add(user);

        return courseRepository.save(course);

    }

    @Override
    public Course updateCourse(Integer id, CourseInput courseInput) {

        Course course = courseRepository.findCourseById(id).orElseThrow(
                () -> new NotFoundException("Course not found with id: " + id )
        );

        User user = userRepository.findById(courseInput.teacherId()).orElseThrow(
                () -> new NotFoundException("User not found with id: " + courseInput.teacherId() )
        );

        course.setTitle(courseInput.title());
        course.setDescription(courseInput.description());
        course.setTeacher(user);
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);

    }

}
