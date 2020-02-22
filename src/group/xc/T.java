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

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class T {
    static class PP {
        public int val;
        public PP next;
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        PP pp = new PP();
        pp.val = 1;
        PP pp1 = new PP();
        pp1.val = 2;
        PP pp2 = new PP();
        pp2.val = 3;
        pp.next = pp1;
        pp1.next = pp2;
        pp2.next = null;
        PP kk = pp;
        kk.val  = 222;
        System.out.println(pp.val);
    }
}
