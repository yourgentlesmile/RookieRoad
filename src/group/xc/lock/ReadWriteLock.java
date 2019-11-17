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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 * 对于同一个资源，多线程读的时候可以不必要进行加锁
 * 写时必须加锁，只能有一个线程对资源进行写操作
 * 读-读能共存
 * 读-写不能共存
 * 写-写不能共存
 * 使用ReentrantReadWriteLock进行读写控制
 */
public class ReadWriteLock {
    Map<Integer,Integer> source = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void put(int a,int b){
        lock.writeLock().lock();
        try {
            source.put(a, b);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
    public int get(int key) {
        lock.readLock().lock();
        try {
            return source.get(key);
        } catch (Exception e){
            e.printStackTrace();

        } finally {
            lock.readLock().unlock();
        }
        return -1;
    }
}
