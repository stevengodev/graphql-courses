package com.foliaco.graphql_course.lesson;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer>{

    @EntityGraph(attributePaths = {"resources"})
    List<Lesson> findByCourseId(Integer courseId);

}
