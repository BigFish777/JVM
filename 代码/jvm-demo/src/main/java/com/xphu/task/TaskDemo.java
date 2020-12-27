package com.xphu.task;

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
