package com.foliaco.graphql_course.course;

import java.util.List;

import com.foliaco.graphql_course.graphql.CourseInput;
import org.springframework.data.domain.Pageable;

import com.foliaco.graphql_course.util.DataPaginator;

public interface CourseService {
    
    Course findById(Integer id);

    DataPaginator<List<Course>> findAll(Pageable pageable);

    Course createCourse(CourseInput courseInput);

    boolean deleteById(Integer id);

    Course enrollStudentToCourse(Integer studentId, Integer courseId);

    Course updateCourse(Integer id, CourseInput courseInput);
}
