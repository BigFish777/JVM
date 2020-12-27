##  一、JVM内存结构

![image-20201226211522164](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226211522164.png)

### 1. 程序计数器

#### 1.1 定义

Program Counter Register 程序计数器（寄存器）

- 作用： 记住下一条jvm指令的地址
- 特点：
  - 是线程私有的
  - 不会存在内存溢出

#### 1.1 作用 

记住下一条jvm指令的地址

![image-20201226212154120](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226212154120.png)

### 2.虚拟机栈

![image-20201226212345568](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226212345568.png)

#### 2.1定义

JAVA Virtual Machine Stacks （Java 虚拟机栈）

#### 2.2栈内存溢出

- 线程运行所需要的内存空间，存放栈帧

- 栈帧：一个栈帧对应着一个方法的调用

- 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法

- 问题辨析：

  - 垃圾回收是否涉栈内存？栈帧内存每一次方法结束后都会弹出栈，所以不用内存管理，所以垃圾回收不用管理

  - 栈内存分配的越大越好吗？

    - 设置大小 ：-Xss size
      - -Xss 1024k
      - -Xss 2m
    - 栈内存大小只是和栈的高度相关，只是和递归的次数有关，设置大了反而会出现 栈的数量\线程数量变少。因为电脑的内存的大小是固定的

  - 方法内的局部变量是否线程安全？

    - 如果方法内部变量没有逃离方法作用域的访问，他是线程安全的

    - 如果是局部变量引用了对象，并且逃离了方法作用域范围，就要考虑线程安全问题了

      

      私有的局部变量

      ![image-20201226223124187](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226223124187.png)



​						**static 变量**

   ![image-20201226223249692](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226223249692.png)

#### 案例：

`方法1`被调用, `方法1` ----又调用---> `方法2` 以此类推就会出现下列的图形，压入多个栈 

#### ![image-20201226213832161](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226213832161.png)

源码：

```java
package com.xphu;

/**
 * @Author @我是一颗小土豆
 * @Date 2020/12/26 22:07
 */
public class TaskDemo {
    // main 方法栈帧1
    public static void main(String[] args) {
        test2();
    }

    public static void test2 () {
        System.out.println("方法2");
        test3();
    }

    public static void test3 () {
        System.out.println("方法3");
        test4();
    }

    public static void test4 () {
        System.out.println("方法4");
    }
}

```

#### 入栈

#### 1、main4出栈

![image-20201226221450716](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226221450716.png)



#### 2、方法1

![image-20201226221631942](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226221631942.png)

#### 3、方法3 方法4 依次入栈

![image-20201226221727840](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226221727840.png)





#### 出栈

#### 1、方法4 出栈

![image-20201226221822909](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201226221822909.png)

#### 2、方法3 方法2 main方法依次出栈  



#### 2.3线程运行诊断

递归：栈内存溢出 StackOverflowError

```
    public static void test3 () {
        i++;
        System.out.println(i);
       test3();
    }
```

案例1：cpu 占用过高

- 定位：用top 定位那个进程对cpu的占用过高

- ps H -eo pid,tid,%cpu | grep 进程id (用ps 命令进一步定位是哪一个线程引用的cpu过高)
- 在通过jdk提供的 jstack 32655 进程id 查看所有的线程
  - 会得到所有的线程 但是线程id是十六进制的

案例2：程序运行时间过长没有结果

### 3.本地方法栈

![image-20201227145628048](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227145628048.png)

本地方法存放的是非Java代码编写的底层方法的内存空间



### 4.堆

#### 4.1 定义

Heap 堆

- 通过关键字 new ，创建的对象都会使用堆内存
- 特点
  - 线程共享，堆中的对象都需要考虑线程安全的问题
  - 有垃圾回收机制

####  4.2 堆内存溢出



- ![image-20201227150344803](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227150344803.png)

#### 4.2 堆内存诊断![image-20201227155735804](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227155735804.png)

##### 4.2.1内存占用情况的检查 一下代码的堆内存使用情况

```java
package com.xphu.heap;

import java.util.Scanner;

/**
 * @Author @我是一颗小土豆
 * @Date 2020/12/27 15:19
 * 堆内存的检查
 */
public class HeapDemo1 {
    public static void main(String[] args) {
        System.out.println("第一步创建 10Mb的堆内存的空间的byte对象");
        System.out.println("请输入下一步的命令：1确认 2取消");
        Scanner scanner = new Scanner(System.in);
        int nextInt = scanner.nextInt();
        if (nextInt == 1){
            byte[] bytes = new byte[1024 * 1024 * 10];
            System.out.println("第二步 将对象制空，并执行垃圾回收");
            System.out.println("请输入下一步的命令：1确认 2取消");
            int nextInt2 = scanner.nextInt();
            if (nextInt == 1){
                bytes = null;
                System.gc();
            }
            System.out.println("第三步再次查看堆内存的占用情况");
            System.out.println("请输入下一步的命令：1退出程序");
            int nextInt3 = scanner.nextInt();
            if (nextInt3 == 1){
            }
        }

    }
}

```



##### 1.使用 `jmap `

- 首先使用 jps 查看进程id,找到我们运行的进程

- HeapDemo.java

  - ![image-20201227154228365](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227154228365.png)

- 然后在使用 jmap -heap 进程id 查看堆内存的占用情况

  - ![image-20201227154933064](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227154933064.png)

  - ![image-20201227155041432](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227155041432.png)

  - #### 创建 10m 的byte 对象后再次执行 jmap 命令 变为17.6M 大小

  - ![image-20201227155230580](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227155230580.png)

  - 然后执行gc，再看内存大小，内存已经释放，被垃圾收机制回收了。
  - ![image-20201227155436627](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227155436627.png)



##### 2.使用 `jconsole`

- 首先开始启动上个程序，然后控制台执行 ``jconsole`命令

- 他会打开一个图形界面工具，然后我们选择要监测的进程 HeapDemo1，选择不安全连接

  - ![image-20201227160242972](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227160242972.png)

  - ![image-20201227160352651](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227160352651.png)

  - ![image-20201227160509974](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227160509974.png)

  - 然后我们和上一个jmap一样,依次执行查看内存的占用情况即可
  - 创建byte对象 

  - ![image-20201227160642308](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227160642308.png)

  - 执行 gc
  - ![image-20201227160824989](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227160824989.png)

#### 4.3 垃圾回收后，内存占用依然很高

使用 jvisualvm 工具

执行程序后 ，在控制台打开 jvisualvm 工具

![image-20201227164048060](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164048060.png)

选择我们要监控的此程序 HeapDemo1

选择监控台 ----》查看堆内存

![image-20201227164148930](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164148930.png)

在这里可以查看内存占用情况，我们创建byte对象查看一下，内存已经升高 到 100m 多了

![image-20201227164317708](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164317708.png)

然后我们可以通过 `堆 Duap`按钮,查看那个对象占用的内存

![image-20201227164415041](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164415041.png)

选择查找前20

![image-20201227164522014](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164522014.png)

可以看到我们已找到 是这个byte占用的内存

![image-20201227164631263](F:\A-IntelliJ IDEA workspace\JVM\jvm学习笔记.assets\image-20201227164631263.png)