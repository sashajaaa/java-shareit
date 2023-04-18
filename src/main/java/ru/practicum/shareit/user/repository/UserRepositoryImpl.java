package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("UserRepositoryImpl")
public class UserRepositoryImpl implements UserRepository {

    public Map<Long, User> users;
    private Long currentId;

    public UserRepositoryImpl() {
        currentId = 0L;
        users = new HashMap<>();
    }

    @Override
    public User create(User user) {
        if (users.values()
                .stream()
                .noneMatch(u -> u.getEmail().equals(user.getEmail()))) {
            if (isValidUser(user)) {
                if (user.getId() == null) {
                    user.setId(++currentId);
                }
                users.put(user.getId(), user);
            }
        } else {
            throw new UserAlreadyExistsException("User with e-mail=" + user.getEmail() + " already exists.");
        }
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new ValidationException("An empty argument was passed.");
        }
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("User with ID = " + user.getId() + " not found.");
        }
        if (user.getName() == null) {
            user.setName(users.get(user.getId()).getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(users.get(user.getId()).getEmail());
        }
        if (users.values()
                .stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .allMatch(u -> u.getId().equals(user.getId()))) {
            if (isValidUser(user)) {
                users.put(user.getId(), user);
            }
        } else {
            throw new UserAlreadyExistsException("User with e-mail=" + user.getEmail() + " already exists.");
        }
        return user;
    }

    @Override
    public User delete(Long userId) {
        if (userId == null) {
            throw new ValidationException("An empty argument was passed.");
        }
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("User with ID = " + userId + " not found.");
        }
        return users.remove(userId);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("User with ID = " + userId + " not found.");
        }
        return users.get(userId);
    }

    private boolean isValidUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Incorrect user e-mail: " + user.getEmail());
        }
        if ((user.getName().isEmpty()) || (user.getName().contains(" "))) {
            throw new ValidationException("Incorrect user login: " + user.getName());
        }
        return true;
    }
}