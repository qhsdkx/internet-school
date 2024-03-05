package by.andron.exception.handler;

import by.andron.exception.RepositoryException;
import by.andron.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleException(Exception ex, HttpServletRequest request) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),LocalDateTime.now(), request.getRequestURI());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleValidationException(ValidationException ex, HttpServletRequest request) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), request.getRequestURI());
    }
    @ExceptionHandler(ServiceException.class)
    public ExceptionDto handlesServiceException(HttpServletRequest request, ServiceException ex){
        return new ExceptionDto(ex.getMessage(), ex.getHttpStatus().value(), LocalDateTime.now(), request.getRequestURI());
    }

}
