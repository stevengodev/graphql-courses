package com.foliaco.graphql_course.graphql;

import com.foliaco.graphql_course.resource.ResourceType;

public record ResourceInput(
        ResourceType type,
        String url,
        String description,
        Integer lessonId
) {
}
