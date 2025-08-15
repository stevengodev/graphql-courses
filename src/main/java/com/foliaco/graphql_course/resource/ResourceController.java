package com.foliaco.graphql_course.resource;

import java.util.List;

import com.foliaco.graphql_course.graphql.ResourceInput;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ResourceController {

    private ResourceService resourceService;

    @QueryMapping(name = "findResourceById")
    public Resource findResourceById(@Argument(name = "resourceId") Integer id){
        return resourceService.findById(id);
    }

    @MutationMapping(name = "createResource")
    public Resource createResource(@Argument(name = "resourceInput") ResourceInput resourceInput){
        return resourceService.createResource(resourceInput);
    }

    @MutationMapping(name = "updateResource")
    public Resource updateResource(@Argument Integer id,
                               @Argument(name = "resourceInput") ResourceInput resourceInput){

        return resourceService.updateResource(id, resourceInput);
    }

    @MutationMapping(name = "deleteResource")
    public boolean deleteResource(@Argument(name = "id") Integer id){
        return resourceService.deleteById(id);
    }

    @QueryMapping(name = "findAllResourcesPaginated")
    public DataPaginator<List<Resource>> findAllLessonsPaginated(@Argument(name = "page") int page,
                                                               @Argument(name = "size") int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return resourceService.findAll(pageable);
    }

}
