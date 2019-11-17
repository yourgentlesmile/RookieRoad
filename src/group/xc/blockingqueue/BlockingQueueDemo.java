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
package group.xc.blockingqueue;

import java.util.List;

/**
 * 阻塞队列：
 * 当队列为空的时候从队列中获取元素的动作将会被阻塞
 * 当队列满的时候从队列中添加元素的动作将会被阻塞
 * 阻塞队列有以下7种类型：
 * 1、ArrayBlockingQueue: 由数据结构组成的有界阻塞队列
 * 2、LinkedBlockingQueue: 由链表结构组成的有界(但大小默认值为Integer.MAX_VALUE)阻塞队列
 * 3、PriorityBlockingQueue: 支持优先级排序的无界阻塞队列
 * 4、DelayQueue: 使用优先级队列实现的延迟无界阻塞队列
 * 5、SynchronousQueue:不储存元素的阻塞队列，也即单个元素的队列
 * 6、LinkedTransferQueue: 由链表结构组成的无界阻塞队列
 * 7、LinkedBlockingDeque: 由链表结构组成的双向阻塞队列
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        List list = null;
    }
}
