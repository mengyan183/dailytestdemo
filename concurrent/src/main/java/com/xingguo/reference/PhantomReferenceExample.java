/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * PhantomReferenceExample
 *
 * @author guoxing
 * @date 2020/3/31 8:35 AM
 * @since
 */
public class PhantomReferenceExample {
    public static void main(String[] args) {
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        PhantomReference p = new PhantomReference<>(counter, refQueue);
        counter = null;
        System.gc();
        try {
//            Thread.sleep(1000L);
            // Remove是一个阻塞方法，可以指定timeout，或者选择一直阻塞
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                if(p.equals(ref)){
                    System.out.println("由于虚引用的对象无法直接被获取到,所以我们可以通过获取引用的队列中的引用来判断对象是否已被GC");
                }
                // do something
                // 由于虚引用的对象无法直接被获取到,所以我们可以通过获取引用的队列中的引用来判断对象是否已被GC
            }
            // 使用虚引用的对象无法直接通过引用获取到
            Object o = p.get();
        } catch (InterruptedException e) {
            // Handle it
        }
    }
}
