/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.bitmap;

/**
 * BitMapDemo
 * 位图
 * TODO:使用位图实现海量图库判重功能
 * @author guoxing
 * @date 2020/5/29 5:05 PM
 * @since
 */
public class BitMapDemo {

    class BitMap { // Java中char类型占16bit，也即是2个字节
        private char[] bytes;
        private int nbits;

        public BitMap(int nbits) {
            this.nbits = nbits;
            this.bytes = new char[nbits / 16 + 1];
        }

        public void set(int k) {
            if (k > nbits) return;
            // 计算在位图数组中的下标
            int byteIndex = k / 16;
            // 计算对应索引存储的数据
            int bitIndex = k % 16;
            bytes[byteIndex] |= (1 << bitIndex);
        }

        public boolean get(int k) {
            if (k > nbits) return false;
            int byteIndex = k / 16;
            int bitIndex = k % 16;
            return (bytes[byteIndex] & (1 << bitIndex)) != 0;
        }
    }
}
