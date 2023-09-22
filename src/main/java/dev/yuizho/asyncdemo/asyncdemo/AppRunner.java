package dev.yuizho.asyncdemo.asyncdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Component
public class AppRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
    private final AsyncTaskService asyncTaskService;

    public AppRunner(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("========= start tasks");

        CompletableFuture[] futures =
                IntStream.rangeClosed(1, 20)
                        .mapToObj(asyncTaskService::startHeavyTask)
                        .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
        logger.info("========= finished!!!!!");
    }
}
