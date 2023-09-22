package dev.yuizho.asyncdemo.asyncdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AsyncDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncDemoApplication.class, args).close();
    }

    @Bean
    public Executor taskExecutor() {
        // https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskExecutor.html#getQueueCapacity()
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("TaskExecutor-");
        executor.initialize();
        return executor;
    }
}
