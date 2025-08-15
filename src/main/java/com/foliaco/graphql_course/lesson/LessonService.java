package com.foliaco.graphql_course.lesson;

import java.util.List;

import com.foliaco.graphql_course.graphql.LessonInput;
import org.springframework.data.domain.Pageable;

import com.foliaco.graphql_course.util.DataPaginator;

public interface LessonService {

    Lesson findById(Integer id);

    DataPaginator<List<Lesson>> findAll(Pageable pageable);

    Lesson createLesson(LessonInput lessonInput);

    boolean deleteById(Integer id);

    Lesson updateLesson(Integer id, LessonInput lessonInput);
}
