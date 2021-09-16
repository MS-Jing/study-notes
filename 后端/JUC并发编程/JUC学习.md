# JUC概述

## 什么是JUC

JUC 是java.util.concurrent工具包的简称。这是一个处理线程的工具，从JDK1.5开始出现的

## 线程和进程

**进程( Process )**是计算机中的程序关于某数据集合上的一次运行活动，**是系**
**统进行资源分配和调度的基本单位**,是操作系统结构的基础。

**线程( thread )是操作系统能够进行运算调度的最小单位。**

## 线程状态

创建、就绪、运行、阻塞、死亡

可以通过Thread类中的State枚举类来看：

```java
public enum State {
    NEW, // 创建
    RUNNABLE, //就绪状态
    BLOCKED, //阻塞状态
    WAITING, //等待状态  一直等，不见不散
    TIMED_WAITING,//等待状态 有个等待时间，过时不候
    TERMINATED; // 停止状态
}
```

## Wait和Sleep方法的区别

+ sleep是Thread方法的今天类，wait是object的方法。
+ sleep不会释放锁，也不需要占用锁。wait会释放锁，但是调用前当前线程拥有该资源的锁（代码需要在synchronized中），可以理解为wait释放锁和cpu而sleep只释放CPU
+ 都可以被interrupted方法中断。

## 并行和并发的区别

并发：同一时刻多个线程访问同一个资源

并行：多项任务同时执行

# Lock锁

## Lock和synchronized区别

+ synchronized是java的关键字，Lock只是一个 接口可以通过实现了该接口的类实现同步操作
+ synchronized在代码块执行结束后自行释放锁，Lock需要用户手动释放锁
+ Lock可以让等待锁的线程响应中断，而synchronized却不行。使用synchronized的等待的线程会一直等待下去，不能响应中断
+ Lock可以知道有没有成功获取到锁，而synchronized却不行
+ Lock可以提高多个线程进行读操作的效率

在性能上来说,如果竞争资源不激烈,两者的性能是差不多的,而当竞争资源非常激烈时(即有大量线程同时竞争) , 此时Lock的性能要远远优于synchronized。

## ReentrantLock

```java
class Ticket {
    private int number = 30;
    private Lock lock = new ReentrantLock();

    public void sale() {
        //加锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + " : 卖出：" + (number--) + " 还剩:" + number);
            }
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
```

# 线程通信

## synchronized实现

```java
public class Share {
    private int number = 0; //共享资源

    public synchronized void add() throws InterruptedException {
        while (number == 1) {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "->" + number);
        this.notify();
    }

    public synchronized void sub() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "->" + number);
        this.notify();
    }

    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    share.add();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "add").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    share.sub();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "sub").start();
    }
}
```

## Lock实现

```java
public class Share {
    private int number = 0; //共享资源
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void add() throws InterruptedException {
        lock.lock();
        try {
            while (number == 1) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "->" + number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void sub() throws InterruptedException {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "->" + number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    share.add();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "add").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    share.sub();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "sub").start();
    }
}
```



# 集合线程安全问题

我们知道当多个线程向ArrayList中添加数据会造成ConcurrentModificationException异常的问题，而且添加的数据也有问题。

这是因为ArrayList不是线程安全的

```java
public class Test {

    public List<String> list = new ArrayList<>();

    public void add() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.add(Thread.currentThread().getName());
        System.out.println(list);
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                test.add();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                test.add();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                test.add();
            }
        },"C").start();

    }
}
```

## 解决方案一（Vector）

Vector也是List接口的实现类

我看可以看一下Vector的add方法:

```java
public synchronized boolean add(E e) {
    // 当前列表被修改的次数
    modCount++;
    //判断当前列表是否需要扩容
    ensureCapacityHelper(elementCount + 1);
    // 向列表中添加元素
    elementData[elementCount++] = e;
    return true;
}
```

我们可以看到和ArrayList的很像，但是方法上面添加了synchronized关键字，所以Vector是线程安全的。

## 解决方案二(Collections)

Collections是util包下的他里面有synchronizedList静态方法用来让列表线程安全。我们可以这样来使我们的列表安全：

```java
public List<String> list = Collections.synchronizedList(new ArrayList<>());
```

为什么这样可以让我们的列表线程安全了呢？追进去看看：

```java
public static <T> List<T> synchronizedList(List<T> list) {
    return (list instanceof RandomAccess ?
            new SynchronizedRandomAccessList<>(list) :
            new SynchronizedList<>(list));
}
```

这里先判断当前列表是否是可随机访问的然后返回SynchronizedRandomAccessList和SynchronizedList并把list给了他们（其实SynchronizedRandomAccessList是SynchronizedList的子类）。到这里我们可以猜测他可能是使用了静态代理模式将我们的线程不安全的列表由一个SynchronizedList来进行代理。

我们看看他的构造方法:

```java
SynchronizedList(List<E> list) {
    super(list);
    this.list = list;
}
```

我们追进SynchronizedList的add方法看看:

```java
public void add(int index, E element) {
    //其实追到他的父类mutex 就是this,或者你需要传递一个要同步的对象
    synchronized (mutex) {
        list.add(index, element);
    }
}
```

看到这里可以看到，他是先获取自身的this对象（或者同步对象）的锁，然后再去代理list进行add操作。所以Collections就是利用静态代理模式代理线程不安全的列表在外面进行加锁达到一个线程安全的目的的

## 解决方案三(CopyOnWriteArrayList)

CopyOnWriteArrayList是JUC包提供的。他用到了写时复制。当有多个线程读的时候不受影响也不加锁。但是当需要写的时候，先复制一份原有的然后在复制的添加新的元素再将旧的替换成新的。

我们看一下他的构造方法:

```java
public CopyOnWriteArrayList() {
    setArray(new Object[0]);
}
```

这里创建了一个长度为0的对象数组。我们再进入到他的add方法看看:

```java
public boolean add(E e) {
    //进行了加锁
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        //获取当前数组并复制一个比当前数组元素多一个长度的新数组
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        //将要添加的元素赋值给复制的新数组的最后一位
        newElements[len] = e;
        //将复制的新的数组替换掉旧的数组
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

所以。当需要写的时候也是进行了加锁，只有一个线程进行写入。但是又不会影响读的效率。



# HashSet线程安全问题

## 解决方案一(CopyOnWriteArraySet)

我们知道HashSet和ArrayList一样，也不是线程安全的。我们可以通过JUC提供的CopyOnWriteArraySet来解决这个问题。其实他的底层用的CopyOnWriteArrayList来实现的，只是在add方法中进行了一次索引，如果当前元素存在就返回false不添加。

```java
public boolean addIfAbsent(E e) {
    Object[] snapshot = getArray();
    return indexOf(e, snapshot, 0, snapshot.length) >= 0 ? false :
    addIfAbsent(e, snapshot);
}
```

## 解决方案二(Collections)

我们也可以通过Collections的静态方法synchronizedSet来获取一个线程安全的set,原理和synchronizedList是一样的:

```java
Collections.synchronizedSet(new HashSet<>());
```



# HashMap线程安全问题

## 解决方案一(ConcurrentHashMap)

JUC提供的ConcurrentHashMap可以用来解决HashMap线程安全的问题

## 解决方案二(Collections)

我们也可以通过Collections的静态方法synchronizedMap来获取一个线程安全的map,原理和synchronizedList是一样的:

```java
Collections.synchronizedMap(new HashMap<>())
```

