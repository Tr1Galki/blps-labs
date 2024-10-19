package lab.user_service.transaction.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationService {
    private final ConcurrentHashMap<String, CountDownLatch> confirmationMap = new ConcurrentHashMap<>();

    @Async
    public CompletableFuture<Boolean> waitForConfirmation(String transactionId, long timeout, TimeUnit unit) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        confirmationMap.put(transactionId, latch);

        try {
            // Ожидание подтверждения в течение заданного времени
            return CompletableFuture.completedFuture(latch.await(timeout, unit));
        } finally {
            // Очистка после обработки
            confirmationMap.remove(transactionId);
        }
    }


    // Метод, который вызывается при получении подтверждения от moder_service
    public void confirmTransaction(String transactionId) {
        CountDownLatch latch = confirmationMap.get(transactionId);
        if (latch != null) {
            latch.countDown();
        }
    }

}
