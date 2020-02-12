package com.xingguo.concurrent.reason;

/**
 * 测试并发产生的原因:有序性
 * 由于代码执行会存在编译优化和执行优化,可能会导致代码执行顺序发生变化
 *
 * @author xingguo
 * @date 2020-02-12 5:34 PM
 * @since 1.0.0
 */
public class Order {

    /**
     * public class Singleton {
     *     // private static Singleton instance;
     *     private static volatile Singleton instance;
     *     public static Singleton getInstance() {
     *         if (instance == null) {
     *             synchronized (Singleton.class) {
     *                 if (instance == null) {
     *                     instance = new Singleton();
     *                 }
     *             }
     *         }
     *         return instance;
     *     }
     * }
     *
     * 在上述获取单例代码中,当多线程并发初始化时,有可能会由于指令重排的问题导致出现NPE;
     * 具体分析在 如果A线程获取到同步锁后在执行 instance = new Singleton();这里有三条指令 分配内存->初始化->地址指向instance,但是指令重排后顺序可能变成 分配内存->地址指向instance->初始化; 当A线程执行时如果执行到初始化之前,线程切换到B,B在执行时instance==null为true,就不会执行初始化操作直接return instance,B线程就会出现NPE
     *
     * 解决的方法为在instance变量上定义volatile关键字就可以保证该代码不会发生代码重排
     */

}
