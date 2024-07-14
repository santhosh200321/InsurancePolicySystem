package com.example.Insurance_policy_App.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(InsuranceNotFoundException.class)
    public ResponseEntity<ErrorMessage> InsuranceNotFound(InsuranceNotFoundException exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorMessage> PolicyNotFound(InsuranceNotFoundException exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(PolicyNotActiveException.class)
    public ResponseEntity<ErrorMessage> PolicyNotClaim(InsuranceNotFoundException exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> CustomerNotFound(InsuranceNotFoundException exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
