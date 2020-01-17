/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package group.xc.algorithm;

import java.util.Stack;

public class SearchA2DMatrix {
    public static void main(String[] args) {
//        int[][] m = {{1,3,5,7},{10,11,16,20},{23,30,34,50}};
        int[][] m = {{1}};
//        int[][] m = {{-8,-6,-5,-4,-2,-1,-1,0,2,4,5,7,7,7,7,9,9,9,9,11},{12,14,15,16,18,20,20,20,21,21,22,23,23,25,25,25,26,27,29,30},{31,31,32,32,33,35,37,39,39,39,40,41,43,44,46,48,48,48,48,50},{52,54,55,57,57,58,58,60,62,64,65,65,65,67,69,71,71,73,74,74},{75,76,78,78,80,82,82,82,84,85,85,87,87,89,90,90,91,93,93,94},{96,98,100,102,104,105,107,109,111,113,113,115,115,117,119,119,120,122,122,124},{126,127,128,130,130,130,130,132,132,133,134,136,137,138,140,141,141,143,144,146},{148,150,151,152,154,156,157,158,159,161,161,162,162,164,164,165,167,168,169,169},{171,173,173,175,176,178,179,181,182,183,184,184,184,185,186,186,186,186,187,189},{190,192,192,193,195,196,197,197,198,198,198,198,198,199,201,203,204,206,208,208},{209,210,211,212,212,212,214,214,216,217,218,218,219,221,222,224,225,227,229,229},{230,230,230,231,233,233,234,235,237,237,238,238,240,240,242,242,244,246,246,247},{249,251,252,252,254,254,256,256,257,258,259,260,260,261,263,265,266,267,267,269},{271,273,273,274,274,274,276,276,276,278,279,280,280,280,282,284,284,286,286,287},{289,290,290,291,293,293,293,293,295,296,296,297,298,299,299,301,302,304,306,308},{309,310,311,311,312,312,314,315,317,319,320,322,323,324,324,324,326,328,329,331},{332,334,335,337,337,339,341,343,345,347,348,348,348,348,348,350,350,350,351,352},{353,355,355,356,357,358,360,361,361,361,362,364,364,364,365,366,368,370,370,372},{374,376,378,380,382,382,383,384,385,385,387,388,388,390,392,394,394,396,397,399},{400,402,403,403,405,405,407,409,411,411,413,414,415,417,418,419,419,419,421,423}};
        System.out.println(doAction(m,0,0,m.length));
    }
    private static boolean doAction(int[][] arr, int num, int start, int end) {
        if (arr.length == 0 || arr[0].length == 0) return false;
        if (num == arr[0][0]) return true;
        if (num == arr[arr.length - 1][0]) return true;
        int middle = (start + end) / 2;
        if (num < arr[middle][0]) {
            if (num < arr[0][0]) return false;
            if (num > arr[middle-1][0]) {
                boolean flag = true;
                for (int i = 0; i < arr[middle-1].length ; i++) {
                    if (num == arr[middle - 1][i])  return true;
                }
                if (flag) return false;
            }
            return doAction(arr, num, start, middle);
        } else if (num > arr[middle][0] && middle + 1 <= end){
            for (int i = 0; i < arr[middle].length; i++) {
                if (num == arr[middle][i]){
                    System.out.println("第 " + middle + "行 ； 第 " + i + "列" );
                    return true;
                }
            }
            if (middle == arr.length - 1 || middle == 0)  return false;
            return doAction(arr, num, middle, end);
        } else if (num == arr[middle][0]){
            System.out.println("第 " + middle + "行 ; 第 0 列");
            return true;
        }
        return false;
    }
    public static boolean search(int[][] matrix, int target) {
        if(matrix.length == 0 || matrix[0].length == 0) return false;
        int x_bound = matrix.length - 1;
        int y_bound = matrix[0].length - 1;
        Stack<Integer> path_x = new Stack<>();
        Stack<Integer> path_y = new Stack<>();
        boolean mo = true;
        for(int i = 0;i < matrix.length; i++) {
           if(matrix[i][0] > target) {
               path_x.push(Math.min(i - 1, x_bound));
               path_y.push(0);
               mo = false;
               break;
           }
        }
        if(mo) {
            path_x.push(matrix.length - 1);
            path_y.push(0);
        }
        while (!path_x.empty() && !path_y.empty()) {
            int x = path_x.pop();
            int y = path_y.pop();
            if(matrix[x][y] == target) return true;
            if(matrix[Math.min(x + 1,x_bound)][y] == target) return true;
            if(matrix[x][Math.min(y + 1,y_bound)] == target) return true;
            if (x + 1 <= x_bound && matrix[x + 1][y] < target) {
                path_x.push(x + 1);
                path_y.push(y);
            }
            if (y + 1 <= y_bound && matrix[x][y + 1] < target) {
                path_x.push(x);
                path_y.push(y + 1);
            }
        }
        return false;
    }
}
class Node {
    private Node leftChild;
    private Node rightChild;
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}