package blps.lab.utils;

import blps.lab.moderation.exceptions.NoSuchDraftArticleException;
import blps.lab.transaction.exception.TransactionTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NoSuchDraftArticleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto draftArticleNotFound(){
        return new ErrorDto("No such draft article!");
    }

    @ExceptionHandler(TransactionTimeoutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto transactionTimeoutError(){
        return new ErrorDto("Транзакция не завершилась в течение ожидаемого времени, но не волнуйтесь," +
                " наши лучшие разработчики уже работают над этим!");
    }
}
