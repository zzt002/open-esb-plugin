package com.dongdl.springboot1.config.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    /**
     * 健康码线程池
     *
     * @return
     */
    @Bean(value = "healthCodeFixedThreadPool")
    public ExecutorService initService() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("healthCode-thread-%d").build();
        BlockingQueue queue = new LinkedBlockingQueue(1000000);
        return new ThreadPoolExecutor(150, 150, 0, TimeUnit.MILLISECONDS,
                queue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
