package by.andron.service;


import by.andron.dto.UserCreationDto;
import by.andron.mapper.UserMapper;
import by.andron.model.User;
import lombok.RequiredArgsConstructor;
import by.andron.dto.UserDto;
import by.andron.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto).toList();
    }

    public UserDto save(UserCreationDto userCreationDto) {
        User entity = userMapper.toEntity(userCreationDto);
        return userMapper.toDto(userRepository.save(entity));
    }

    public void update(UserCreationDto userCreationDto) {
        userRepository.update(userMapper.toEntity(userCreationDto));
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

}
