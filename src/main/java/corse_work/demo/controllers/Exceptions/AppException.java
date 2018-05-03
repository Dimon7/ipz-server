package corse_work.demo.controllers.Exceptions;


import lombok.*;
import org.springframework.http.HttpStatus;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AppException extends Exception{

    private String errorMessage;
    private HttpStatus httpStatus;


    public AppException(String errorMessage){
        super();
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.BAD_REQUEST;

    }

}