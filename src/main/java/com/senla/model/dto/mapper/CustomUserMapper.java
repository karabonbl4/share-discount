package com.senla.model.dto.mapper;

import com.senla.model.dto.UserDto;
import com.senla.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserMapper {

    private final ModelMapper modelMapper;

    public User map(UserDto userDto){
        User mappedUser = modelMapper.map(userDto, User.class);
        mappedUser.setCredentialsNonExpired(true);
        mappedUser.setAccountNonLocked(true);
        mappedUser.setAccountNonExpired(true);
        mappedUser.setEnabled(true);
        return mappedUser;
    }
}
