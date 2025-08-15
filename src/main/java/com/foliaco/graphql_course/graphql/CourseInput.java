package com.foliaco.graphql_course.graphql;

public record CourseInput(
        String title,
        String description,
        Integer teacherId
) {
}
