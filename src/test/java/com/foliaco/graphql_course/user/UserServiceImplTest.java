package com.foliaco.graphql_course.user;

import com.foliaco.graphql_course.exception.NotFoundException;
import com.foliaco.graphql_course.graphql.UserInput;
import com.foliaco.graphql_course.util.DataPaginator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("123456");
        user.setRol(Role.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void givenId_whenFindById_thenReturnUser() {
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User userFound = userService.findById(1);

        assertNotNull(userFound);
        assertEquals("John Doe", userFound.getName());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void givenIdNotExists_whenFindById_thenThrowException() {
        Integer id = 2;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void whenFindAllUsers_thenReturnUsersPaginated() {
        List<User> users = List.of(user);
        Page<User> page = new PageImpl<>(users, PageRequest.of(0, 10), 1);
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(page);

        DataPaginator<List<User>> dataPaginator = userService.findAll(PageRequest.of(0, 10));

        assertEquals(1, dataPaginator.getTotalPages());
        assertEquals(0, dataPaginator.getCurrentPage());
        assertEquals(1, dataPaginator.getData().size());
        verify(userRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void givenUserInput_whenCreateUser_thenSaveUser() {
        UserInput input = new UserInput("John Doe", "john@example.com", "123456", Role.STUDENT);
        when(userMapper.toUser(input)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userSaved = userService.createUser(input);

        assertNotNull(userSaved);
        assertEquals("John Doe", userSaved.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void givenIdAndUserInputValid_whenUpdateUser_thenSaveUpdatedUser() {
        Integer id = 1;
        UserInput input = new UserInput("Maria gomez", "maria.gomez@example.com", "1234", Role.TEACHER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updateUser = userService.updateUser(1, input);

        assertEquals("Maria gomez", updateUser.getName());
        assertEquals(Role.TEACHER, updateUser.getRol());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void givenId_whenDeleteById_thenRemoveUser() {
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        boolean isDeleted = userService.deleteById(1);

        assertTrue(isDeleted);
        verify(userRepository, times(1)).delete(user);
    }

}
