package lab.moder_service.transaction.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class TransactionService {
//    private final JtaTransactionManager transactionManager;

//    public <T> T executeTransactional(String name, int timeout, Supplier<T> supplier) {
//        try {
//            Transaction transaction = transactionManager.createTransaction(name, timeout);
//            try {
//                T result = supplier.get();
//                transaction.commit();
//                System.err.println("Transaction completed successfully");
//                return result;
//            } catch (Exception e) {
//                transaction.rollback();
//                throw new TransactionTimeoutException();
//            }
//        } catch (Exception e) {
//            throw new TransactionTimeoutException();
//        }
//    }

    public <T> T executeTransactional(String name, int timeout, Supplier<T> supplier) {
        return supplier.get();
    }
}
