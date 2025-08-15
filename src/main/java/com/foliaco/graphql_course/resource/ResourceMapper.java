package com.foliaco.graphql_course.resource;

import com.foliaco.graphql_course.graphql.ResourceInput;
import com.foliaco.graphql_course.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public Resource toResource(ResourceInput resourceInput, Lesson lesson){
        Resource resource = new Resource();
        resource.setType(resourceInput.type());
        resource.setDescription(resourceInput.description());
        resource.setUrl(resourceInput.url());
        resource.setLesson(lesson);
        return resource;
    }

}
