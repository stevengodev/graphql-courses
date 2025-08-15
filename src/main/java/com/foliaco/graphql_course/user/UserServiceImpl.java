package com.foliaco.graphql_course.user;

import java.time.LocalDateTime;
import java.util.List;

import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.UserInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foliaco.graphql_course.util.DataPaginator;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
            () -> new NotFoundException("User not found with id: " + id )
        );
    }

    @Transactional(readOnly = true)
    @Override
    public DataPaginator<List<User>> findAll(Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(pageable);
        return new DataPaginator<>(pageUser.getContent(), pageUser.getTotalPages(), pageUser.getNumber());
    }

    @Transactional
    @Override
    public User createUser(UserInput input) {
        User user = userMapper.toUser(input);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Integer id, UserInput userInput) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found with id: " + id )
        );

        user.setName(userInput.name());
        user.setEmail(userInput.email());
        user.setPassword(userInput.password());
        user.setRol(userInput.rol());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);

    }

    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        
        User user = userRepository.findById(id).orElseThrow(
            () -> new NotFoundException("User not found with id: " + id )
        );

        userRepository.delete(user);
        return true;
    }
    
}
