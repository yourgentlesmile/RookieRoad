package group.xc.algorithm;

public class HeapSort {
    /**
     * 假设节点的下标是i,那么，他父节点的下标就是Math.floor((i-1) / 2),左节点下标是 2 * i + 1,右节点下标是 2 * i + 2
     * @param size 树中有多少个节点
     * @param i 需要heapify的节点下标
     */
    public static void heapify(int[] tree, int size, int i) {
        //判断递归终止条件
        if(i > size) return ;
        int c1 = 2 * i + 0;
        int c2 = 2 * i + 1;
        int max  = i;
        //为了c1与c2不出界
        if(c1 < size && tree[c1] > tree[max]) max = c1;
        if(c2 < size && tree[c2] > tree[max]) max = c2;
        //如果不相等，表示在左右节点中，发现了比i节点更大的值，这样就需要进行根交换
        if(max != i) {
            swap(tree, max, i);
            heapify(tree,size,max);
        }
    }
    public static void heapSort(int[] arr, int size) {
        build_heap(arr,size);
        for (int len = size - 1; len >= 0; len-- ) {
            swap(arr, 0, len);
            heapify(arr,len, 0);
        }
    }
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    public static void build_heap(int[] tree, int size) {
        int last_node = size - 1;
        int parent = (last_node - 1) / 2;
        for (int i = parent; i >= 0 ; i--) {
            heapify(tree, size,i);
        }
    }
    public static void main(String[] args) {
        int[] tree = new int[]{2, 5, 3, 1, 10, 4};
        heapSort(tree,tree.length);
        System.out.println();
    }
}
