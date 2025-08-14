package com.foliaco.graphql_course.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

    @EntityGraph(attributePaths = {"coursesCreated", "coursesEnrolled"})
    Optional<User> findUserById(Integer integer);
}
