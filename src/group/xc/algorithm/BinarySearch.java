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

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = new int[]{7,8,12,22,25,29,45};
        int result = search(48, arr, 0, arr.length-1);
        System.out.println(result);
    }

    /**
     * 树妃美眉的二分查找
     * @param arr
     * @param start
     * @param end
     * @return
     */
    public static int search(int target, int[] arr, int start, int end) {
        int middle = (start + end) / 2;
        int num = 45;
        if (num == arr[middle]) {
            return middle;
        }
        if (start == end) {
            return -1;
        }
        if (num < arr[middle]) {
            return search(num, arr, start, middle);
        }
        return search(num, arr, middle + 1, end);
    }
}
