package group.xc.jdk;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //无返回值
        CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " Complete");
        });
        Integer integer = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " Complete");
            return 0;
        }).whenComplete((value, e) -> {
            System.out.println("*****" + value);
            System.out.println("***** exception" + e);
        }).exceptionally(e -> {
            System.out.println("***** exception : " + e.getMessage());
            return -1;
        }).get();
        System.out.println(integer);
    }
}
