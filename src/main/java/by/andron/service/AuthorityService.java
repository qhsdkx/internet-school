package by.andron.service;

import by.andron.dto.AuthorityCreationDto;
import by.andron.dto.AuthorityDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.AuthorityMapper;
import by.andron.model.Authority;
import by.andron.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    public AuthorityDto findById(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find authority by id in service", HttpStatus.BAD_REQUEST));
        return authorityMapper.toDto(authority);
    }

    public List<AuthorityDto> findAll() {
        try {
            return authorityMapper.toDto(authorityRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all authorities in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public AuthorityDto save(AuthorityCreationDto authorityCreationDto) {
        try {
            Authority entity = authorityMapper.toEntity(authorityCreationDto);
            return authorityMapper.toDto(authorityRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public AuthorityDto update(Long id, AuthorityCreationDto authorityCreationDto) {
        try {
            Authority authority = authorityRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find authority by id in service", HttpStatus.BAD_REQUEST));
            Authority entity = authorityMapper.toEntity(authorityCreationDto);
            updateAuthority(authority, entity);
            authorityRepository.save(authority);
            return authorityMapper.toDto(authority);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            authorityRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateAuthority(Authority authority, Authority source){
        authority.setName(source.getName());
        authority.setRoles(source.getRoles());
    }
}
