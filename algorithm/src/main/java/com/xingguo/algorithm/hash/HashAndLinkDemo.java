/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.hash;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HashAndLinkDemo
 * hash表结合链表
 *
 * @author guoxing
 * @date 2020/4/29 3:06 PM
 * @since
 */
public class HashAndLinkDemo {

    public static void main(String[] args) {

        linkedHashMapDemo();
    }

    public static void linkedHashMapDemo() {
    /*     linkedHashMap在 HashMap的基础上,对每个slot中的Entry链表重新维护了一个双向链表
         对于 java.util.HashMap.Node 是维护的每个slot中的链表数据, 而对于 java.util.LinkedHashMap.Entry是维护的所有slot中所有的java.util.HashMap.Node
         对于 linkedHashMap 双向链表结构指的是 维护了连接了所有Map.Entry,而不是每个slot中链表修改为双向链表
         当 accessOrder 为true 时,如果在Map中存在这个key,则将这个双向链表中的节点移动为尾结点,并不会改变其在slot后的位置
         指定accessOrder 为true 指按照按照访问顺序进行排序,每访问过一次就放到链尾; 否则按照插入顺序进行排序*/
        LinkedHashMap<Integer, Integer> stringStringLinkedHashMap = new LinkedHashMap<>(10,0.75f,true);
        for (int i = 0; i < 5; i++) {
            stringStringLinkedHashMap.put(i, i);
        }
        for (Map.Entry<Integer, Integer> entry : stringStringLinkedHashMap.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("==================================");
        stringStringLinkedHashMap.get(3);
        for (Map.Entry<Integer, Integer> entry : stringStringLinkedHashMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
