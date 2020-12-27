package com.xphu.task;

/**
 * @Author @我是一颗小土豆
 * @Date 2020/12/26 22:07
 */
public class TaskDemo2 {
    static int i = 0;

    // main 方法栈帧1
    public static void main(String[] args) {
//        final StringBuilder sb = new StringBuilder();
//        new Thread(()->{
//            test2(sb,1,1000);
//        }).start();
//        new Thread(()->{
//            test2(sb,1001,2000);
//        }).start();
        test3();
    }

    public static void test1 () {
        // sb是局部变量所以是线程安全的
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        System.out.println(sb);
    }

    public static void test2 (StringBuilder sb,int go,int to) {
        for (int i = go; i < to; i++) {
            sb.append(i+",");
        }
        System.out.println(sb);
    }

    public static void test3 () {
        i++;
        System.out.println(i);
       test3();
    }
}