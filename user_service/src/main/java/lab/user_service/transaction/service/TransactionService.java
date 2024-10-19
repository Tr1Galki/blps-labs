package lab.user_service.transaction.service;

import java.util.function.Supplier;

import jakarta.transaction.Transaction;
import lab.user_service.transaction.exception.TransactionTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final JtaTransactionManager transactionManager;

    public <T> void executeTransactional(String name, int timeout, Supplier<T> supplier) {
        new Thread(() -> {
            try {
                Transaction transaction = transactionManager.createTransaction(name, timeout);
                try {
                    supplier.get();
                    transaction.commit();
                    System.err.println("Transaction completed successfully");
                } catch (Exception e) {
                    log.error("Transaction failed", e);
                    transaction.rollback();
                    throw new TransactionTimeoutException();
                }
            } catch (Exception e) {
                throw new TransactionTimeoutException();
            }

        }).start();
    }

    public <T> void executeTransactional(String name, long timeout, Supplier<T> supplier) {
        new Thread(() -> {
            try {
                try {
                    supplier.get();
                    System.err.println("Transaction completed successfully");
                } catch (Exception e) {
                    log.error("Transaction failed", e);
                    throw new TransactionTimeoutException();
                }
            } catch (Exception e) {
                throw new TransactionTimeoutException();
            }

        }).start();
    }
}
