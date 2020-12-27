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
            byte[] bytes = new byte[1024 * 1024 * 100];
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
