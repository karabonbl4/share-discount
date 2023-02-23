package com.senla.service.impl;

import com.senla.dao.UserRepository;
import com.senla.model.dto.UserDto;
import com.senla.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userService;
    private final static Long ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveUserSuccessfully() {
        final UserDto userDto = mock(UserDto.class);
        final User mapedUser = mock(User.class);
        when(modelMapper.map(userDto, User.class)).thenReturn(mapedUser);
        when(userRepository.save(mapedUser)).thenReturn(mapedUser);
        when(modelMapper.map(mapedUser, UserDto.class)).thenReturn(userDto);

        UserDto actualUser = userService.save(userDto);

        assertNotNull(actualUser);
        assertEquals(userDto, actualUser);
        verify(userRepository).save(mapedUser);
    }

    @Test
    public void shouldFindByIdUserSuccessfully() {
        final UserDto userDto = mock(UserDto.class);
        final User mapedUser = mock(User.class);
        when(modelMapper.map(userDto, User.class)).thenReturn(mapedUser);
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(mapedUser));
        when(modelMapper.map(mapedUser, UserDto.class)).thenReturn(userDto);

        UserDto actual = userService.findById(ID);

        assertNotNull(actual);
        assertEquals(userDto, actual);
        verify(userRepository).findById(ID);
    }

    @Test
    public void shouldFindAllUsersSuccessfully() {
        userService.findAll();

        verify(userRepository).findAll();
    }

    @Test
    public void shouldDeleteUserSuccessfully() {
        final User mapedUser = mock(User.class);
        when(userRepository.getReferenceById(ID)).thenReturn(mapedUser);

        userService.delete(ID);

        verify(userRepository).delete(mapedUser);
    }

    @Test
    public void shouldUpdateUserSuccessfully() {
        final UserDto userDto = mock(UserDto.class);
        final User mapedUser = mock(User.class);
        when(modelMapper.map(userDto, User.class)).thenReturn(mapedUser);
        when(userRepository.saveAndFlush(mapedUser)).thenReturn(mapedUser);
        when(modelMapper.map(mapedUser, UserDto.class)).thenReturn(userDto);

        UserDto actualUser = userService.update(userDto);

        assertNotNull(actualUser);
        assertEquals(userDto, actualUser);
        verify(userRepository).saveAndFlush(mapedUser);
    }
}