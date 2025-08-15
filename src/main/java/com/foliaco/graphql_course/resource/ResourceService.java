package com.foliaco.graphql_course.resource;

import java.util.List;

import com.foliaco.graphql_course.graphql.ResourceInput;
import org.springframework.data.domain.Pageable;

import com.foliaco.graphql_course.util.DataPaginator;

public interface ResourceService {

    Resource findById(Integer id);

    DataPaginator<List<Resource>> findAll(Pageable pageable);

    Resource createResource(ResourceInput resourceInput);

    boolean deleteById(Integer id);

    Resource updateResource(Integer id, ResourceInput resourceInput);
}
