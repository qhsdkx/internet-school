package by.andron.service.impl;


import by.andron.annotation.Profiling;
import by.andron.dto.UserCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.UserMapper;
import by.andron.model.User;
import by.andron.service.UserService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.UserDto;
import by.andron.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Profiling
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("Cannot find user by id in service", HttpStatus.BAD_REQUEST));
        return userMapper.toDto(user);
    }

    public List<UserDto> findAll(int page, int size) {
        try {
            return userRepository.findAll(page, size).stream()
                    .map(userMapper::toDto).toList();
        } catch (Exception e) {
            throw new ServiceException("Cannot find all users in service", HttpStatus.BAD_REQUEST);
        }
    }

    public UserDto save(UserCreationDto userCreationDto) {
        try {
            User entity = userMapper.toEntity(userCreationDto);
            return userMapper.toDto(userRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save user in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, UserCreationDto userCreationDto) {
        try {
            userRepository.update(id, userMapper.toEntity(userCreationDto));
        } catch (Exception e) {
            throw new ServiceException("Cannot update this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

}
