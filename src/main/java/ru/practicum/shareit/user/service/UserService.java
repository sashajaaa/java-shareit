package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper mapper;

    @Autowired
    public UserService(@Qualifier("UserRepositoryImpl") UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.mapper = userMapper;
    }

    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(mapper::toUserDto)
                .collect(toList());
    }


    public UserDto getUserById(Long id) {
        return mapper.toUserDto(userRepository.getUserById(id));
    }

    public UserDto create(UserDto userDto) {
        return mapper.toUserDto(userRepository.create(mapper.toUser(userDto)));
    }

    public UserDto update(UserDto userDto, Long id) {
        if (userDto.getId() == null) {
            userDto.setId(id);
        }
        return mapper.toUserDto(userRepository.update(mapper.toUser(userDto)));
    }

    public UserDto delete(Long userId) {
        return mapper.toUserDto(userRepository.delete(userId));
    }
}