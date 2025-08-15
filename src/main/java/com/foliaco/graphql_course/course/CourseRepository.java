package com.foliaco.graphql_course.course;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    // This method is for avoiding the n + 1 problem

    @EntityGraph( attributePaths = {"students", "teacher", "lessons"} )
    Optional<Course> findCourseById(Integer id);

}
