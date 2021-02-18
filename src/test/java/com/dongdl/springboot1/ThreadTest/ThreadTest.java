package com.dongdl.springboot1.ThreadTest;

import com.dongdl.springboot1.util.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ThreadTest {

    private AtomicBoolean flag = new AtomicBoolean(false);

    private ExecutorService executorService;

    @Test
    public void thread() {
        Vector<String> vector = new Vector<>();

        List<String> list = getList();
        CompletableFuture[] completableFutures = list.stream().map(str ->
                CompletableFuture.supplyAsync(() -> testTodo(str), executorService).thenAccept(vector::add).exceptionally(e -> {
                    throw new RuntimeException(e.getMessage());
                })
        ).toArray((CompletableFuture[]::new));

        CompletableFuture.allOf(completableFutures).exceptionally(e -> {
            throw new RuntimeException(e.getMessage());
        }).join();
        System.out.println(Arrays.toString(vector.toArray()));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void getThread() {
        System.out.println("555");
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-thread-%d").build();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
        executorService =  new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS,
                queue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private List<String> getList() {
        ArrayList<String> list = Lists.newArrayList();
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        return list;
    }

    private String testTodo(String str) {
        if (flag.get()) {
            return null;
        }

        int i = 0;
        do {
            i++;
            if (i > 3) {
                log.error(String.format("str:%s,i:%d", str, i));
                flag.set(true);
                throw new RuntimeException("ERROR: " + str + " more than " + i + " times");
            }

            String resp = doRequest();

            if (resp != null) {
                break;
            }

        } while (true);
        return str;
    }

    private String doRequest() {
        String url = "http://localhost:8086/test/redis2?num=5";
        String response = null;
        try {
            response = HttpUtil.doGet(url,10,10);
        } catch (Exception e) {
            ;
        }
        return response;
    }


}
