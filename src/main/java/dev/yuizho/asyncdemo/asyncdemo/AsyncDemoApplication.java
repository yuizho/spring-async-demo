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
        // CorePoolサイズは常にアイドル状態のタスク
        // taskの量がQueueCapacityを超えるとMaxPoolサイズまでThreadが増える
        // ただし、MaxPoolサイズまで増えた状態で、更にQueueCapacityを超えるとTaskRejectedExceptionの例外が発生してしまう
        //
        // 今回の例だと、タスクの総数: 20 なので、
        // 10タスクが10スレッドによってさばかれて、残りの10タスクはキューイングされる
        // 最初の10タスクが完了したら、残りの10タスクがさばかれることになる。
        // これを踏まえて、タスクの総数: 21 だった場合、
        // 10タスクが10スレッドによってさばかれて、残りの11タスクはキューイングされることになる。
        // このときキューイングされるタスクの量が10(QueueCapacity)を超えるのでTaskRejectedExceptionの例外が発生してしまう
        // https://blog.ik.am/entries/443
        //
        // QueueCapacityを超えた分作られるThreadの数はQueueCapacityの値とタスクの数に依存する
        // 例えば、アイドルスレッド1で、固定の5タスクを5スレッドさばきたかったらMaxPoolSize: 5, QueueCapacity: 0にすればよい
        // このとき、QueueCapacity: 1にすると4スレッドでさばかれ、2にするとスレッドでさばかれる感じになる
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("TaskExecutor-");
        executor.initialize();
        return executor;
    }
}
