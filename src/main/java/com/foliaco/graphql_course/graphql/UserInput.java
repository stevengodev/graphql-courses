package com.foliaco.graphql_course.graphql;

import com.foliaco.graphql_course.user.Role;

public record UserInput(
        String name,
        String email,
        String password,
        Role rol
) {}