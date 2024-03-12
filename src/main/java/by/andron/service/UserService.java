package by.andron.service;


import by.andron.aspect.annotation.Cacheable;
import by.andron.dto.UserCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.UserMapper;
import by.andron.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.andron.dto.UserDto;
import by.andron.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Cacheable
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find user by id in service", HttpStatus.BAD_REQUEST));
        return userMapper.toDto(user);
    }

    public List<UserDto> findAll(){
        try {
            return userMapper.toDto(userRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all users in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public UserDto save(UserCreationDto userCreationDto) {
        try {
            User entity = userMapper.toEntity(userCreationDto);
            return userMapper.toDto(userRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save user in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, UserCreationDto userCreationDto) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find user by id in service", HttpStatus.BAD_REQUEST));
            User entity = userMapper.toEntity(userCreationDto);
            updateUser(user, entity);
            userRepository.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateUser(User user, User source){
        user.setId(source.getId());
        user.setName(source.getName());
        user.setSurname(source.getSurname());
        user.setLogin(source.getLogin());
        user.setPassword(source.getPassword());
        user.setRoles(source.getRoles());
        user.setCourses(source.getCourses());
        user.setTeacherCourses(source.getTeacherCourses());
        user.setBirthDate(source.getBirthDate());
        user.setCourseResults(source.getCourseResults());    }

}
