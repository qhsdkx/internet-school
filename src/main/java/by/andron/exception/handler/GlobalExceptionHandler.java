package by.andron.exception.handler;

import by.andron.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ExceptionDto handleRepositoryException(HttpServletRequest request, Exception ex, HttpStatus status){
        return new ExceptionDto(ex.getMessage(), status.value(), LocalDateTime.now(), request.getRequestURI());
    }

    @ExceptionHandler(ServiceException.class)
    public ExceptionDto handlesServiceException(HttpServletRequest request, Exception ex, HttpStatus status){
        return new ExceptionDto(ex.getMessage(), status.value(), LocalDateTime.now(), request.getRequestURI());
    }

}
