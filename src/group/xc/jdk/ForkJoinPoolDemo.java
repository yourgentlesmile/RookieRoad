package group.xc.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 */
public class ForkJoinPoolDemo {
    static class MyTask extends RecursiveTask<Integer> {
        private final Integer THRESHOLD = 10;
        private int begin;
        private int end;
        private int result;

        public MyTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if((end - begin <= THRESHOLD)) {
                for (int i = begin; i <= end ; i++) {
                    result = result + i;
                }
            }else {
                int middle = (end + begin) / 2;
                MyTask task01 = new MyTask(begin, middle);
                MyTask task02 = new MyTask(middle + 1, end);
                task01.fork();
                task02.fork();
                result = task01.join() + task02.join();
            }
            return result;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyTask myTask = new MyTask(0, 10);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);
        System.out.println(submit.get());
        forkJoinPool.shutdown();
    }
}
