package com.marketplace.services;

import com.marketplace.domain.user.UserResponseDTO;
import com.marketplace.domain.user.UserUpdateResponseDTO;
import com.marketplace.domain.user.exceptions.DuplicatedUserException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserDTO;
import com.marketplace.domain.user.exceptions.UserNotFoundException;
import com.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Matheus Alves
 */
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO create(UserDTO userDTO) {

        Optional<User> existingUser = repository.findByEmailAddress(userDTO.emailAddress());

        if(existingUser.isPresent()) {
            throw new DuplicatedUserException(
                    String.format("User with emailAddress=%s already exists", userDTO.emailAddress())
            );
        }

        User user = repository.save(new User(userDTO));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmailAddress());
    }

    public UserUpdateResponseDTO update(UUID id, UserDTO userDTO) {

        User user = findById(id);

        user.setName(userDTO.name());

        user = repository.save(user);

        return new UserUpdateResponseDTO(user.getId(), user.getName());

    }

    protected User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User not found for id=%s", id))
                );
    }

    public UserResponseDTO get(UUID id) {
        User user = this.findById(id);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmailAddress());
    }

    public List<UserResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(), user.getName(), user.getEmailAddress()))
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }

}
