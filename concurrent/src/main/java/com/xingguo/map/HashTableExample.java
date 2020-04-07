/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.map;

import java.util.Hashtable;

/**
 * HashTableExample
 *
 * @author guoxing
 * @date 2020/4/1 3:46 PM
 * @since
 */
public class HashTableExample {
    public static void main(String[] args) {
        testNullKey();
    }

    public static void testNullKey(){
        Hashtable<String, String> stringStringHashtable = new Hashtable<>();
        // 由于直接使用Object中的hashcode计算key的hash值,所以key也不为空
        stringStringHashtable.put(null,"1");
    }
}
