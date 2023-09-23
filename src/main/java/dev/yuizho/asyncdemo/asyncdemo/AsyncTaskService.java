package dev.yuizho.asyncdemo.asyncdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncTaskService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskService.class);

    @Async
    public CompletableFuture<Integer> startHeavyTask(int id) {
        logger.info("start: {}", id);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("end  : {}", id);

        return CompletableFuture.completedFuture(id);
    }
}
