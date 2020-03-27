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
package group.xc;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class T {
    static class A {
    }
    static A a;
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        a = new A();
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println("-=-=-=-=-=-=-=-");
        Thread thread = new Thread(){
            @Override
            public void run() {
                synchronized (a){
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-=-=-=-=" + Integer.toHexString(a.hashCode()));
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
