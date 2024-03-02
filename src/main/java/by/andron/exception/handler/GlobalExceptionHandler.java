package by.andron.exception.handler;

import by.andron.exception.RepositoryException;
import by.andron.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ExceptionDto handlesServiceException(HttpServletRequest request, ServiceException ex){
        return new ExceptionDto(ex.getMessage(), ex.getHttpStatus().value(), LocalDateTime.now(), request.getRequestURI());
    }

}
