package by.andron.security.auth;

import by.andron.dto.UserCreationDto;
import by.andron.mapper.UserMapper;
import by.andron.security.JwtService;
import by.andron.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    public AuthenticationResponse register(UserCreationDto request){
        UserCreationDto user = UserCreationDto.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .login(request.getLogin())
                .birthDate(request.getBirthDate())
                .roleIds(request.getRoleIds())
                .build();
        userService.save(user);
        String accessToken = jwtService.generateToken(userMapper.toEntity(user));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(user));
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserCreationDto user = userService.findByLogin(request.getLogin());
        String accessToken = jwtService.generateToken(userMapper.toEntity(user));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(user));
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken (
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userLogin;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        userLogin = jwtService.extractUserLogin(refreshToken);

        if(userLogin != null){
            UserCreationDto user = userService.findByLogin(userLogin);
            UserDetails userDetails = userMapper.toEntity(user);

            if(jwtService.isTokenValid(refreshToken, userDetails)){
                String accessToken = jwtService.generateToken(userDetails);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
