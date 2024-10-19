package lab.user_service.utils;

import lab.user_service.security.exceptions.UserAlreadyExistsException;
import lab.user_service.security.exceptions.UserNotFoundException;
import lab.user_service.transaction.exception.TransactionTimeoutException;
import lab.user_service.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    TransactionService transactionService;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto userNotFound(){
        return new ErrorDto("User not found!");
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


    public void t() {
        transactionService.executeTransactional(
                "1",
                1,
                () -> {
                    System.out.println("1");
                    return 1;
                }
        );
    }
}
