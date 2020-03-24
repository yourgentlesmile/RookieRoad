# Java8 新特性

## 函数式接口(jdk-1.8开始提供)

四大函数式接口：

![image-20200219124534449](assets/image-20200219124534449.png)  

## ForkJoinPool....

分支合并框架。  

ForkJoinPool, ForkJoinTask, RecursiveTask  

![image-20200219140803661](assets/image-20200219140803661.png)

把大任务分解成小任务，来并行执行(fork)，计算完后再合并起来(join)

![image-20200219141309298](assets/image-20200219141309298.png)

## 异步回调

**CompletableFuture**  

可以实现无返回调用，也可以实现有返回调用  

![image-20200219144552143](assets/image-20200219144552143.png)  

# HashMap

## 类继承结构

![image-20200222160628614](assets/image-20200222160628614.png)

## 底层数组存储结构

![image-20200222162214482](assets/image-20200222162214482.png)  

## Integer.highestOneBit(i)

获得i这个数二进制形式最左边的最高位为1，其他位补零的数。实际效果就是，返回给定数字的向下最接近2次幂的数字。例如9，向下最接近2次幂的数就为8。  

https://blog.csdn.net/jessenpan/article/details/9617749

## JDK1.7 roundUpToPowerOf2(i) ----- JDK 1.8 tableSizeFor(i)

```java
//1.8
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

![image-20200221124726553](assets/image-20200221124726553.png)  

获得给定数字向上最靠近2次幂的数字，例如9，那么获得的就是16这个数字。**减一是考虑了当给定的数字就是2次幂的情况**  

## 为什么数组长度要为2次幂

1、方便用与运算来替代取模操作

```java
//1.8
if ((p = tab[i = (n - 1) & hash]) == null)
//1.7用的是indexFor方法，内容是一样的
```

> 例如数组长度为16，hashcode为0101 1010，得到数组下标的运算为 hashcode & (length - 1)
>
> 那么  length - 1 = 0000 1111
>
> ​         hashcode = 0101 1010
>
> 做一次与运算得到 0000 1010，正好是取模的值，由此也可以看出，长度-1之后，位为1的区段就是数组的取值范围

## 为什么获得hashcode之后，高16位要与低16位进行与运算

```java
//1.8
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

因为，与运算，两个位只要有一位变化，结果也会发生变化。这样让高位也参与运算，提高散列性。  

同时由于，刚开始hashmap的数组长度很小，在进行与运算的时候，hashcode参与运算的位数基本在低16位当中，所以需要让高位也参与到计算当中

## JDK1.7与JDK1.8的扩容条件与时机不同

在1.7中，扩容有两个条件  

![image-20200221132606810](assets/image-20200221132606810.png)  

1、当前size大于阈值  

2、**当前所要放入的数组所在位置不为空**  

并且，插入元素之前先判断需不需要扩容

而1.8中，则是先插入元素再判断需不需要扩容

## JDK1.7与1.8新增元素的插入位置不同

1.7当中，是用头插法将hash冲突的元素插入到链表当中  

1.8当中，是用尾插发将hash冲突的元素插入到链表当中  

**1.8的Hashmap将头插法改成尾插法，目的是解决多线程环境下扩容时链表死循环的问题**

## JDK1.8中链表转为红黑树的条件

1、链表长度超过TREEIFY_THRESHOLD(默认是8)

2、哈希表中所有元素超过MIN_TREEIFY_CAPACITY(默认是64)

## Put方法

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    //tab: 引用当前HashMapde 散列表
    //p: 表示当前的散列表元素
    //n: 表示散列表数组的长度
    //i: 表示路由寻址的结果(也就是数据要放入数组位置的下标)
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    
    //延迟初始化逻辑，第一次调用putVal时会初始化HashMap对象中的最耗费内存的散列表
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    //如果找到的位置没有数据，则将当前KV封装成Node对象并放入数组当中
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        //e: 不为null的话，找到了一个与当前要插入的kv一致key的元素
        //k: 表示临时的一个key
        Node<K,V> e; K k;
        
        //表示位置中的该元素与你当前插入的元素的key完全一致，表示后续需要进行替换操作
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        //是否已经树化
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                //如果已经到了链表尾部也没找到一个与当前要插入的key一致的node，则将KV插入链表尾部
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    //判断链表长度如果大于等于树化阈值，则将链表树化，-1是因为bincount是从0开始
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        //树化操作
                        treeifyBin(tab, hash);
                    break;
                }
                //找到了与当前需要插入kv的node
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        //e不等于null，条件成立说明找到了一个与当前插入元素key完全一致的数据，需要进行替换
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            //返回旧值
            return oldValue;
        }
    }
    //表示散列表结构被修改次数，替换Node元素中的value不计数(也就是更新元素)
    ++modCount;
    //插入新元素，size自增，判断自增后的大小是否需要扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

## resize方法-扩容

为什么需要扩容：为了解决哈希冲突导致的链化影响查询效率问题，扩容会缓解该问题。  

```java
final Node<K,V>[] resize() {
    //oldTab: 引用扩容前的哈希表
    Node<K,V>[] oldTab = table;
    //oldCap: 表示扩容之前table数组的长度
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    //oldThr: 表示扩容之前的扩容阈值，触发本次扩容的阈值
    int oldThr = threshold;
    //newCap: 扩容之后table数组的大小
    //newThr: 扩容之后再次触发扩容的条件
    int newCap, newThr = 0;
    //条件成立说明，HashMap中的散列数组已经初始化过了，是一次正常的扩容
    if (oldCap > 0) {
        //如果当前长度大于等于最大长度，则设置长度为上限，并直接返回旧table，表示无法再扩容
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        //正常扩容，oldCap左移一位实现翻倍，并且赋值给newCap，newCap小于数组最大值限制 且 扩容之前阈值>=16
        //这种情况下，下一次扩容阈值等于当前阈值翻倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    //oldCap == 0，说明HashMap中的散列表是null，在以下几种情况，oldThr会不为0
    //1.new HashMap(initCap, loadFactor)
    //2.new HashMap(initCap)
    //3.new HashMap(map)，并且这个Map有数据
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    //oldCap = 0, oldThr = 0
    //只会在调用new HashMap()的时候走到
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY; //16
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);  //12
    }
    //newThr为0时，通过newCap和loadFactor计算出一个newThr
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    //说明，HashMap本次扩容之前数组已经创建
    if (oldTab != null) {
        
        for (int j = 0; j < oldCap; ++j) {
            //当前Node节点
            Node<K,V> e;
            //成立，表示数组当前位置有数据
            if ((e = oldTab[j]) != null) {
                //方便JVM GC时回收内存
                oldTab[j] = null;
                //第一种情况: 表示此处未产生hash碰撞，直接将元素重新计算位置即可
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                //第二种情况: 当前节点已经树化
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    //第三种情况，当前位置已经形成链表
                    //低位链表: 存放在扩容之后的数组的下标位置，与当前数组的下标位置一致
                    Node<K,V> loHead = null, loTail = null;
                    //高位链表: 存放在扩容之后的数组的下标位置为 当前数组下标位置 + 扩容之前的数组长度
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    //将链表中的元素拆分成低位链表与高位链表
                    do {
                        next = e.next; 
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;	
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        //这里的next可能会指向旧值，由于低位链表已到了末尾，直接赋null即可
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

## get方法

```java
final Node<K,V> getNode(int hash, Object key) {
    //tab: 引用当前HashMap的散列表
    //first: 桶位中的头元素
    //e: 临时node元素
    //n: table数组长度
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        if ((e = first.next) != null) {
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```

## remove方法

```java
public V remove(Object key) {
    Node<K,V> e;
    return (e = removeNode(hash(key), key, null, false, true)) == null ?
        null : e.value;
}
```

```java
final Node<K,V> removeNode(int hash, Object key, Object value,
                           boolean matchValue, boolean movable) {
    //tab: 引用当前HashMap中的散列表
    //p: 当前Node元素
    //n: 表示散列表长度
    //index: 表示寻址结果
    Node<K,V>[] tab; Node<K,V> p; int n, index;
    //判断散列表是否存在，位置是否为空
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (p = tab[index = (n - 1) & hash]) != null) {
        //进入if说明桶位是有数据的，需要进行查找操作，并且删除
        //node: 查找到的结果
        //e: 当前node的下一个元素
        Node<K,V> node = null, e; K k; V v;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            node = p;
        else if ((e = p.next) != null) {
            //到这个分支说明当前桶位要么是链表，要么是红黑树
            if (p instanceof TreeNode)
                node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
            else {
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key ||
                         (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
        }
        //判断node不为空表示根据key查找到了需要删除的数据
        if (node != null && (!matchValue || (v = node.value) == value ||
                             (value != null && value.equals(v)))) {
            if (node instanceof TreeNode)
                ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
            else if (node == p)
                tab[index] = node.next;
            else
                p.next = node.next;
            ++modCount;
            --size;
            afterNodeRemoval(node);
            return node;
        }
    }
    return null;
}
```

## replace方法

内部使用的就是get方法所调用的getNode方法

![image-20200222205252635](assets/image-20200222205252635.png)

# Java 集合接口

![img](assets/timg.jpg)

![img](assets/timg-1582976109478.jpg)

# 标记型接口

## Serializable

## Cloneable

## RandomAccess

此接口以表示实现类支持快速(通常为恒定时间)随机访问。  

此接口的主要目的是允许通用算法更改其行为，以便于在应用于随机访问列表或顺序访问列表时提供良好的性能。

所以，实现了该接口之后，该集合的随机访问效率是要高于顺序访问的。

### 随机访问

```java
for(int i = 0;i < list.size();i++) list.get(i);
```

### 顺序访问

```java
for(Iterator i = list.iterator(); i.hasNext();) i.next()
```

### 开发经验

当接收到一个List的子类对象的时候，可以判断子类是否有实现RandomAccess接口，通过以上判断来采取不同的遍历方式。(主要针对ArrayList与LinkedList)，如果实现：推荐使用随机访问的方式进行遍历；否则，使用顺序访问遍历  

