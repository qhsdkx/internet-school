package by.andron.service;

import by.andron.dto.UserCreationDto;
import by.andron.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(Long id);
    List<UserDto> findAll();
    UserDto save(UserCreationDto userCreationDto);
    void update(Long id, UserCreationDto userCreationDto);
    void delete(Long id);
}
