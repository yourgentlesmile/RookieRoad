# Java8 新特性

## 函数式接口(jdk-1.8开始提供)

四大函数式接口：

![image-20200219124534449](assets/image-20200219124534449.png)  

## ForkJoinPool....

分支合并框架。  

ForkJoinPool, ForkJoinTask, RecursiveTask  

![image-20200219140803661](assets/image-20200219140803661.png)

把大任务分解成小任务，来并行执行(fork)，计算完后再合并起来(join)

![image-20200219141309298](assets/image-20200219141309298.png)

## 异步回调

**CompletableFuture**  

可以实现无返回调用，也可以实现有返回调用  

![image-20200219144552143](assets/image-20200219144552143.png)  