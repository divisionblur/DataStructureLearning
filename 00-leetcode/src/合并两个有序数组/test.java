package 合并两个有序数组;

import java.util.function.Function;

/**
 * @author joy division
 * @date 2020/10/26 21:49
 */
public class test {
    public static void main(String[] args) {
        Function<Integer,Employee> fun = id -> new Employee(id);
        Employee instance = fun.apply(9090);
        System.out.println(instance);
    }
}
