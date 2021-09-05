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

