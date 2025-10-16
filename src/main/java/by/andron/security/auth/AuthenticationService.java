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
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(request);
        String accessToken = jwtService.generateToken(userMapper.toEntity(request));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(request));
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserCreationDto user = userService.findByLogin(request.getLogin());
        String accessToken = jwtService.generateToken(userMapper.toEntity(user));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(user));
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public void refreshToken (
            String authHeader,
            HttpServletResponse response
    ) throws IOException {
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
                AuthenticationResponse authResponse = new AuthenticationResponse(
                        jwtService.generateToken(userDetails),
                        jwtService.generateRefreshToken(userDetails)
                );
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
