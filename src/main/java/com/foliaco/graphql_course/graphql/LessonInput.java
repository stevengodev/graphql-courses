package com.foliaco.graphql_course.graphql;

public record LessonInput(
        String title,
        String content,
        Integer courseId
) {}
