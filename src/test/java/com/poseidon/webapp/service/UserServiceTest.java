package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.anyInt;
        import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private UserRepository userRepository;

    private Optional<User> user;
    private Optional<User> user2;

    private List<User> users;


    @BeforeEach
    private void init() {
        user = Optional.of(new User("name", "fullname","password","ADMIN"));
        user.get().setId(1);
        user2 = Optional.of(new User("name2", "fullname2","password2","ADMIN"));
        users = List.of(user.get(),user2.get());
    }

  @Test
    public void existsByUserNameTest(){
        when(userRepository.existsByUsername("name")).thenReturn(false);
        boolean result = userService.existsByUserName(user.get().getUsername());
        assertFalse(result);
    }

    @Test
    public void findAllTest(){
        when(userRepository.findAll()).thenReturn(users);
        userService.findAll();
        assertEquals(users.size(),2);
    }

    @Test
    public void createTest(){
      when(userRepository.save(user.get())).thenReturn(user.get());
      userService.create(user.get());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void updateTest(){
        when(userRepository.save(user.get())).thenReturn(user.get());
        userService.update(user.get());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void findByIdTest(){
        when(userRepository.findById(1)).thenReturn(user);
        User userFind = userService.findById(1).get();

        assertEquals(user.get().getFullname(),userFind.getFullname());
    }

    @Test
    void deleteTest() {
        userService.delete(user.get());

        verify(userRepository).delete(any(User.class));
    }
}
