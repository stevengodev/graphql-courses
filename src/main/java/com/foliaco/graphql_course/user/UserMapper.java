package com.foliaco.graphql_course.user;

import com.foliaco.graphql_course.graphql.UserInput;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

   public User toUser(UserInput input){
       return User.builder()
               .name(input.name())
               .email(input.email())
               .password(input.password())
               .rol(input.rol())
               .build();

   }

}
