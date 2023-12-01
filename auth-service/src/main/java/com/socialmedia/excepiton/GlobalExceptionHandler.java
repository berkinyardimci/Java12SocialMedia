package com.socialmedia.excepiton;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthManagerException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(AuthManagerException ex) {
        HttpStatus httpStatus = ex.getErrorType().getHttpStatus();

        ErrorType errorType = ex.getErrorType();
        ErrorMessage errorMessage = createError(errorType,ex);
        errorMessage.setMessage(ex.getMessage());

        return new ResponseEntity(errorMessage, httpStatus);
    }

    private ErrorMessage createError(ErrorType errorType, Exception e){
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

}
