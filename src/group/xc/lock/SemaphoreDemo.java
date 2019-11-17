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

import java.util.concurrent.Semaphore;

/**
 * 信号量：
 * 主要是两个目的
 * 1.一个是用于多个共享资源的互斥使用
 * 2.用于并发线程数的控制
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //3个槽位
        //还有一个构造参数 public Semaphore(int permits, boolean fair)
        //第二个参数表示是否使用公平锁 ，默认使用非公平锁
        Semaphore slot = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    slot.acquire();
                    System.out.println("Hold 1 slot");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("release 1 slot");
                  slot.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
