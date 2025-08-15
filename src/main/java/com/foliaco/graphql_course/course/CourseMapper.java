package com.foliaco.graphql_course.course;

import com.foliaco.graphql_course.graphql.CourseInput;
import com.foliaco.graphql_course.user.User;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    Course toCourse(CourseInput courseInput, User user){
        Course course = new Course();
        course.setTitle(courseInput.title());
        course.setDescription(courseInput.description());
        course.setTeacher(user);
        return course;
    }

}
