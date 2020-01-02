package group.xc.jvm;

import java.util.Arrays;

public class GCRootDemo {
    private int _1MB = 1 * 1024 * 1024;
    private byte[] bytesArray = new byte[100 * _1MB];

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9};
        System.out.println(arr);
        int num = xcAction(arr,0, arr.length - 1);
        System.out.println(num);
    }
    private static int xcAction (int[] arr,int start,int end) {
        if(start == end) return arr[start];
        int divide = (start + end) / 2;
        System.out.printf("xcAction(arr, %d, %d) + xcAction(arr, %d, %d) \n",start, divide, divide + 1, end);
        return xcAction(arr,start,divide) + xcAction(arr,divide + 1,end);
    }
    private static int sfmmAction(int[] arr) {
        int middle = arr.length/2;
        int result = 0;
        int startIndex = 0;
        if (middle > 2) {
            int[] arr1 = Arrays.copyOfRange(arr, startIndex, middle);
            int num = sfmmAction(arr1);
            int[] arr2 = Arrays.copyOfRange(arr, middle, arr.length);
            int num3 = sfmmAction(arr2);
            return num + num3;
        }
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
        }
        return result;
    }

}
