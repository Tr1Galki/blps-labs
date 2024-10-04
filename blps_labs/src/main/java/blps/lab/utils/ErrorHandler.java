package blps.lab.utils;

import blps.lab.auth.exceptions.UserNotFoundException;
import blps.lab.moderation.exceptions.NoSuchDraftArticleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto userNotFound(){
        return new ErrorDto("User not found!");
    }

    @ExceptionHandler(NoSuchDraftArticleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto draftArticleNotFound(){
        return new ErrorDto("No such draft article!");
    }
}
