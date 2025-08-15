package com.foliaco.graphql_course.lesson;

import com.foliaco.graphql_course.course.Course;
import com.foliaco.graphql_course.graphql.LessonInput;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toLesson(LessonInput lessonInput, Course course){
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonInput.title());
        lesson.setContent(lessonInput.content());
        lesson.setCourse(course);

        return lesson;
    }

}
