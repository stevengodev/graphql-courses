package com.foliaco.graphql_course.user;

import java.util.List;

import com.foliaco.graphql_course.graphql.UserInput;
import org.springframework.data.domain.Pageable;

import com.foliaco.graphql_course.util.DataPaginator;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return the user with the given ID
     */
    User findById(Integer id);

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable pagination information
     * @return a paginated list of users
     */
    DataPaginator<List<User>> findAll(Pageable pageable);

    /**
     * Creates a new user with the provided input data.
     *
     * @param input the user input data containing details for the new user
     * @return created user
     */
    User createUser(UserInput input);

    /**
     * Updates an existing user with the provided input data.
     *
     * @param id the unique identifier of the user to be updated
     * @param userInput the user input data containing updated details
     * @return the updated user
     */
    User updateUser(Integer id, UserInput userInput);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be deleted
     * @return true if the user was successfully deleted, throws an exception otherwise
     */
    boolean deleteById(Integer id);

}
