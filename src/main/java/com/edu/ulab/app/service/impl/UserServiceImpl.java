package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserStorage storage;
    private final UserMapper mapper;

    public UserServiceImpl(UserStorage storage, UserMapper mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = storage.save(mapper.userDtoToUser(userDto));
        return mapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = storage.update(id, mapper.userDtoToUser(userDto));
        return mapper.userToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = storage.get(id);
        return mapper.userToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        storage.delete(id);
    }

    public List<Long> getBookIdList(UserDto userDto) {
        User user = storage.get(userDto.getId());
        return user.getBookIdList();
    }
}
