package group.xc.jdk;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionInterface {
    public static void main(String[] args) {
        //函数接口中都有apply方法，

        Consumer<Integer> consumer = System.out::println;
        //与前者等价
        Consumer<Integer> consumer_copy = s -> System.out.println(s);
        //调用一次就获得当前时间戳
        Supplier<Long> supplier = () -> new Date().getTime();
        //传入一个字符串，获得他的长度
        Function<String, Integer> function = s -> s.length();
        //传入一个字符串，输出是否为空
        Predicate<String> predicate = s -> s.isEmpty();

        //调用
        consumer.accept(1);
        System.out.println(supplier.get());
        System.out.println(function.apply("123"));
        System.out.println(predicate.test("asd"));
        
    }
}
