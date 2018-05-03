package corse_work.demo.controllers.Exceptions;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log
@Component
@ControllerAdvice
@EnableWebMvc
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.info("ExceptionHandler=> "+ex.getMessage());
        error.setMessage("Please contact administrator");
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> AppExceptionHandler(AppException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(ex.getHttpStatus().value());
        error.setMessage(ex.getErrorMessage());
        return new ResponseEntity<ErrorResponse>(error,ex.getHttpStatus());
    }

    public static void CheckValid(BindingResult result) throws AppException {
        String errroMsg = "ERROR: Please check the fields: ";

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                log.info(error.getDefaultMessage());
                errroMsg+=error.getDefaultMessage()+", ";
            }
            errroMsg = errroMsg.substring(0, errroMsg.length() - 2);
            log.info(errroMsg);
            throw new AppException(errroMsg);
        }
    }
}
