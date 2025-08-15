package com.foliaco.graphql_course.user;

import java.util.List;

import com.foliaco.graphql_course.graphql.UserInput;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
    
    private UserService userService;

    @QueryMapping(name = "findUserById")
    public User findUserById(@Argument(name = "userId") Integer id){
        return userService.findById(id);
    }

    @MutationMapping(name = "createUser")
    public User createUser(@Argument(name = "userInput") UserInput userInput){
        return userService.createUser(userInput);
    }

    @MutationMapping(name = "updateUser")
    public User updateUser(@Argument Integer id,
            @Argument(name = "userInput") UserInput userInput){
        return  userService.updateUser(id, userInput);
    }

    @MutationMapping(name = "deleteUser")
    public boolean deleteUser(@Argument(name = "id") Integer id){
        return userService.deleteById(id);
    }

    @QueryMapping(name = "findAllUsersPaginated")
    public DataPaginator<List<User>> findAllUsersPaginated(@Argument(name = "page") int page, 
    @Argument(name = "size") int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userService.findAll(pageable);
    }

}
