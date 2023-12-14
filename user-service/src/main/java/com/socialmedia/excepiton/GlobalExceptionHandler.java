package com.socialmedia.excepiton;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserManagerException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(UserManagerException ex) {
        HttpStatus httpStatus = ex.getErrorType().getHttpStatus();

        ErrorType errorType = ex.getErrorType();
        ErrorMessage errorMessage = createError(errorType,ex);
        errorMessage.setMessage(ex.getMessage());

        return new ResponseEntity(errorMessage, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(IllegalArgumentException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorType errorType = ErrorType.INVALID_STATUS;
        ErrorMessage errorMessage = createError(errorType,ex);
        errorMessage.setMessage(errorType.getMessage());

        return new ResponseEntity(errorMessage, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorType errorType = ErrorType.PARAMETER_NOT_VALID;
        List<String> fields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e-> fields.add(e.getField()+": " + e.getDefaultMessage()));
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setFields(fields);
        return  new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }



    private ErrorMessage createError(ErrorType errorType, Exception e){
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

}
