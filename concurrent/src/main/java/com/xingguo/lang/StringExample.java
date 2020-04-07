/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.lang;

import java.util.ArrayList;

/**
 * StringExample
 *
 * @author guoxing
 * @date 2020/3/31 11:28 AM
 * @since
 */
public class StringExample {
    public static void main(String[] args) {
//        test3();
        test2();
//        testStringConstantSpace();
    }

    public static void testShort() {
        short s = 1;
        s+=1;
    }

    //java.lang.OutOfMemoryError: Java heap space
    public static void testStringConstantSpace() {
        ArrayList<String> strings = new ArrayList<>();
        String i = "string";
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }
            String str = i + i;
            i = str;
            strings.add(str.intern());
        }
    }

    public static void test3() {
        String a = new String("1");
        String b = "1";
        String intern = a.intern();
        // false
        System.out.println(a == b);
        // true
        System.out.println(intern == b);
        // 此时指向的为堆中的对象地址;常量池中不存在"23"
        String d = new String("2") + new String("3");
        // 显式声明 "23"放到常量池中
        String e = "23";
        // 由于此时常量池中已存在"23",直接返回常量池中的地址
        String intern1 = d.intern();
        // true
        System.out.println(intern1 == e);
        // false
        System.out.println(d == e);
    }

    public static void test2() {
        // 执行的操作为 1:将"1"放入常量池中;2:生成一个new String 对象,a的地址指向新生成的对象
        String a = new String("1");
        // 发现常量池中已存在"1",则返回常量池中"1"的地址
        String intern = a.intern();
        // 显式声明发现常量池中已存在"1",则将b指向常量池中的地址
        String b = "1";
        // false
        System.out.println(a == b);
        // true
        System.out.println(intern == b);
        // true
        System.out.println(intern == b);
        String d = new String("2") + new String("3");
        // 此时常量池中不存在"23",会把d的引用存放到常量池中
        d.intern();
        // 显式声明时发现已存在"23"的引用地址,则直接返回常量池中存在的地址,不会在常量池中生成一个新的"23"常量 ;
        // 实际上等价于 String e = d;
        String e = "23";
        // true
        System.out.println(d == e);
    }


    public static void test1() {

        String a = "1"; // 指向方法区中的字符串常量池

//        String exist = "123";
//        System.out.println(System.identityHashCode(exist));
        // 等价于 String b = new StringBuilder().append(a).append("23").toString();
        // java.lang.StringBuilder.toString 实际调用的是 java.lang.String#String(byte[], byte);所以b指向的是一个在堆区的地址,且并不存在于常量池中
        // 23实际是存在于常量池中的
        String b = a + "2" + "3";
        System.out.println(System.identityHashCode(b));
        // 当调用b.intern 如果此时常量池中不存在 123,会将123从对中转移到常量池中,且该常量的地址为b的地址(这实际上有一个问题,会导致b对应的堆内存引用链增加,导致堆内存长时间无法释放转移到老年代);
        // 反之如果常量池中已存在,则intern会返回常量池中已存在常量的地址
        String intern = b.intern();
        // 如果此时将b置为空,并执行gc;
        b = null;
        System.gc();
        System.out.println(System.identityHashCode(intern));
        System.out.println(intern == b);
        // 而对于这一步实际上是在编译成class之后已经做了优化 实际为 String c = "123";
        String c = "1" + "2" + "3";
        String d = "123";
        // 当使用字符串变量进行拼接时,会生成新的字符串对象,且不存在于常量池中;所以b和c的内存地址不同
        System.out.println(b == c);
        System.out.println(intern == c);
        // 当使用字符串进行直接赋值时,JVM会首先在元组区的字符串常量词中查找是否已存在相同的字符串,如果存在相同的字符串则将已存在的字符串地址直接返回给对象引用;
        // 反之,则生成新的字符串对象
        System.out.println(d == c);
        // 打印对象实际内存地址
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(d));

        System.out.println(System.identityHashCode(a));
        // 当前操作实际分为 1: a+"1"生成一个新的对象;2:将新的对象的地址返回为a
        a += "1";
        System.out.println(System.identityHashCode(a));
        String e = "11";
        System.out.println(System.identityHashCode(e));
        String f = new String("123");
        System.out.println(System.identityHashCode(f));
    }
}
