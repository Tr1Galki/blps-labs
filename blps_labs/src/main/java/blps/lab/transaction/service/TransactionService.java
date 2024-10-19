package blps.lab.transaction.service;

import java.util.function.Supplier;

import blps.lab.transaction.exception.TransactionTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @Qualifier("transactionManager")
    private final PlatformTransactionManager transactionManager;

    public <T> void executeTransactional(Supplier<T> supplier) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("pinTx");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            try {
                T result = supplier.get();
                transactionManager.commit(status);
                System.err.println("Transaction completed successfully");
            } catch (Exception e) {
                transactionManager.rollback(status);
                throw new TransactionTimeoutException();
            }
        } catch (Exception e) {
            throw new TransactionTimeoutException();
        }
    }
}
