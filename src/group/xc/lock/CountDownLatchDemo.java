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
package group.xc.lock;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 用于某个线程需要等待其他一定数量的线程干完某些事之后再继续执行
 *
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        //需要等待5个线程执行完，才能继续执行
        CountDownLatch trigger = new CountDownLatch(5);
        try {
            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    System.out.println("I'm finish -> " + Thread.currentThread().getName());
                    trigger.countDown();
                },String.valueOf(i)).start();
            }
            trigger.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main finish.");
    }
}
