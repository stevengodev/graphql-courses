package com.foliaco.graphql_course.course;

import com.foliaco.graphql_course.graphql.CourseInput;
import com.foliaco.graphql_course.util.DataPaginator;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @QueryMapping(name = "findCourseById")
    public Course findCourseById(@Argument(name = "courseId") Integer id){
        return courseService.findById(id);
    }


    @MutationMapping(name = "createCourse")
    public Course createCourse(@Argument(name = "courseInput") CourseInput courseInput){
        return courseService.createCourse(courseInput);
    }

    @MutationMapping(name = "updateCourse")
    public Course updateCourse(@Argument Integer id,
                               @Argument(name = "courseInput") CourseInput courseInput){

        return courseService.updateCourse(id, courseInput);

    }

    @MutationMapping(name = "deleteCourse")
    public boolean deleteCourse(@Argument(name = "id") Integer id){
        return courseService.deleteById(id);
    }

    @MutationMapping(name = "enrollStudentToCourse")
    public Course enrollStudentToCourse(@Argument(name = "studentId") Integer studentId,
                                        @Argument(name = "courseId") Integer courseId){

        return courseService.enrollStudentToCourse(studentId, courseId);
    }

    @QueryMapping(name = "findAllCoursesPaginated")
    public DataPaginator<List<Course>> findAllCoursesPaginated(@Argument(name = "page") int page,
                                                             @Argument(name = "size") int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return courseService.findAll(pageable);
    }

}
