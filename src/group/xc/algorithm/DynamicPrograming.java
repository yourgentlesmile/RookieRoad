package group.xc.algorithm;

import java.util.Arrays;

public class DynamicPrograming {
    /**
     * dp
     * 状态转移方程: fib(n) + fib(n - 1) = fib(n + 1)
     *
     * 但是这样是O(2^n)
     * 因为会产生很多重叠的子问题
     * 如下所示：
     * compute 6	compute 5	compute 4	compute 3	compute 2
     * compute 1	compute 2	compute 3	compute 2	compute 1
     * compute 4	compute 3	compute 2	compute 1	compute 2
     * 8
     * 所以可以使用备忘录的方法，将已经计算过的保存下来
     * 如下所示
     * compute 6	compute 5	compute 4	compute 3	compute 2
     * compute 1	compute 2
     * 8
     * 没有重复计算
     */
    public static int fib_recursive(int i,int[] note) {
        System.out.print("compute " + i + "\t");
        if(i == 1 || i == 2) return 1;
        int cur = (note[i - 1] == -1 ? fib_recursive(i - 1,note) : note[i - 1]) + (note[i - 2] == -1 ? fib_recursive(i - 2,note) : note[i - 2]);
        note[i] = cur;
        return cur;
    }

    /**
     * 非递归版
     */
    public static int fib_loop(int i) {
        if(i == 1 || i == 2) return 1;
        int[] note = new int[6];
        note[0] = 1;
        note[1] = 1;
        for (int j = 2; j < i; j++) {
            int a = note[j - 2];
            int b = note[j - 1];
            note[j] = a + b;
        }
        return note[i - 1];
    }

    /**
     * 判断所给数字在数组中是否能找到若干个数字相加等于它
     */
    public static boolean rec_subset(int[] arr, int i, int remain) {
        if(remain == 0) return true;
        if(i == 0) return arr[0] == remain;
        if(arr[i] > remain) return rec_subset(arr,i - 1,remain);
        else {
            return rec_subset(arr,i - 1, remain - arr[i]) ||
            rec_subset(arr, i - 1, remain);
        }
    }

    public static void main(String[] args) {
        int[] note = new int[]{3, 34, 4, 12, 5, 2};
        System.out.println(rec_subset(note, note.length - 1,12));
    }

}
