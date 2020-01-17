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

import java.util.Arrays;

/**
 * 这个二路归并，非有序合并
 */
public class SimpleBinaryCombination {

    private static int xcAction(int[] arr,int start,int end) {
        if(start == end) return arr[start];
        int divide = (start + end) / 2;
        return xcAction(arr,start,divide) + xcAction(arr,divide + 1,end);
    }

    /**
     * 树妃美眉的二路归并
     * @param arr
     * @return
     */
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
