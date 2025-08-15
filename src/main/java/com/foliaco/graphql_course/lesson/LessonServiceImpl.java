package com.foliaco.graphql_course.lesson;

import java.time.LocalDateTime;
import java.util.List;

import com.foliaco.graphql_course.course.Course;
import com.foliaco.graphql_course.course.CourseRepository;
import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.LessonInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService{

    private LessonRepository lessonRepository;

    private CourseRepository courseRepository;

    private LessonMapper lessonMapper;

    @Override
    public Lesson findById(Integer id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Lesson not found with id: " + id )
        );
    }

    @Override
    public DataPaginator<List<Lesson>> findAll(Pageable pageable) {
        Page<Lesson> pageLesson = lessonRepository.findAll(pageable);
        return new DataPaginator<>(pageLesson.getContent(), pageLesson.getTotalPages(), pageLesson.getNumber());
    }

    @Override
    public Lesson createLesson(LessonInput lessonInput) {

        Course course = courseRepository.findCourseById(lessonInput.courseId()).orElseThrow(
                () -> new NotFoundException("Course not found with id: " + lessonInput.courseId())
        );

        Lesson lesson = lessonMapper.toLesson(lessonInput, course);
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean deleteById(Integer id) {

        Lesson lesson = lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Lesson not found with id: " + id)
        );

        lessonRepository.delete(lesson);
        return true;

    }

    @Override
    public Lesson updateLesson(Integer id, LessonInput lessonInput) {

        Lesson lesson = lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Lesson not found with id: " + id)
        );

        Course course = courseRepository.findCourseById(lessonInput.courseId()).orElseThrow(
                () -> new NotFoundException("Course not found with id: " + lessonInput.courseId())
        );

        lesson.setTitle(lessonInput.title());
        lesson.setContent(lessonInput.content());
        lesson.setCourse(course);
        lesson.setUpdatedAt(LocalDateTime.now());
        return lessonRepository.save(lesson);

    }

}
