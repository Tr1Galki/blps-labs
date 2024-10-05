package blps.lab.utils;

import blps.lab.security.exceptions.UserAlreadyExistsException;
import blps.lab.security.exceptions.UserNotFoundException;
import blps.lab.moderation.exceptions.NoSuchDraftArticleException;
import blps.lab.transaction.TransactionTimeoutException;
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto userAlreadyExistsError(){
        return new ErrorDto("User with this username or email already exist!");
    }

    @ExceptionHandler(TransactionTimeoutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto transactionTimeoutError(){
        return new ErrorDto("Транзакция не завершилась в течение ожидаемого времени, но не волнуйтесь," +
                " наши лучшие разработчики уже работают над этим!");
    }
}
