/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.bean;

/**
 * BeanInitExample
 *
 * @author guoxing
 * @date 2020/4/4 9:06 AM
 * @since
 */
public class BeanInitExample {
    private String init = "1";
    private static String staticInit = "staticInit";

    static {
        System.out.println(" 父类 1 : 初始化静态代码块 以及 静态变量 " + staticInit);
    }

    {
        System.out.println("父类2 : 实例化非静态代码块 以及 非静态变量" + init);
    }

    public BeanInitExample() {
        System.out.println("父类3 : 执行构造方法");
    }

    public static void main(String[] args) {
//        new SonBeanInitExample();
        try {
            // 默认初始化
//            Class.forName("com.xingguo.bean.SonBeanInitExample");
            // 如果设置不初始化,则只会执行父类初始化操作,初始化父类的静态代码
//            Class.forName("com.xingguo.bean.SonBeanInitExample",false,ClassLoader.getSystemClassLoader());
            ClassLoader.getSystemClassLoader().loadClass("com.xingguo.bean.ClassLoaderExample");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class SonBeanInitExample extends BeanInitExample {
    private String sonInit = "1";
    private static String sonStaticInit = "sonStaticInit";

    static {
        System.out.println(" 子类 1 : 初始化静态代码块 以及 静态变量 " + sonStaticInit);
    }

    {
        System.out.println(" 子类 2 : 实例化非静态代码块 以及 非静态变量" + sonInit);
    }

    public SonBeanInitExample() {
        System.out.println(" 子类 3 : 执行构造方法");
    }
}

class ClassLoaderExample {
    private String sonInit = "1";
    private static String sonStaticInit = "sonStaticInit";

    static {
        System.out.println("初始化静态代码块 以及 静态变量 " + sonStaticInit);
    }

    {
        System.out.println("实例化时 非静态代码块 以及 非静态变量" + sonInit);
    }

    public ClassLoaderExample() {
        System.out.println("实例化");
    }
}
