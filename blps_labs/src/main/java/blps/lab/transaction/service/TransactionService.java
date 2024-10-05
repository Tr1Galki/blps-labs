package blps.lab.transaction.service;

import java.util.function.Supplier;

import blps.lab.transaction.exception.TransactionTimeoutException;
import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final JtaTransactionManager transactionManager;

    public <T> T executeTransactional(String name, int timeout, Supplier<T> supplier) {
        try {
            Transaction transaction = transactionManager.createTransaction(name, timeout);
            try {
                T result = supplier.get();
                transaction.commit();
                System.err.println("Transaction completed successfully");
                return result;
            } catch (Exception e) {
                transaction.rollback();
                throw new TransactionTimeoutException();
            }
        } catch (Exception e) {
            throw new TransactionTimeoutException();
        }
    }
}
