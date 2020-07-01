/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.queue;

import java.util.function.Consumer;

/**
 * ArrayBlockingQueueDemo
 * {@link java.util.concurrent.ArrayBlockingQueue}
 * {@link com.lmax.disruptor.dsl.Disruptor}
 * //TODO 通过加锁或cas来避免并发情况下,写入覆盖/重复消费的问题
 *
 * @author guoxing
 * @date 2020/6/19 4:58 PM
 * @since
 */
public class ArrayBlockingQueueDemo {

    static class CircleArrayQueue {
        private Long[] data;
        private int size = 0, head = 0, tail = 0;

        public CircleArrayQueue(int size) {
            this.size = size;
            this.data = new Long[size];
        }

        public boolean add(Long element) {
            // 判断环是否已满; 这里实际只能存储 size -1 个元素,会有一个空闲插槽位置
            if ((tail + 1) % size == head) {
                return false;
            }
            // tail代表空闲slot位置,tail肯定小于size;  当并发情况下由于赋值和tail指针移动非原子操作,因此会存在tail位置的数据被后续的数据覆盖,而tail移动了多次,造成了空slot
            data[tail] = element;
            // 避免tail 的大小超过size
            tail = (tail + 1) % size;
            return true;
        }

        public Long poll() {
            //判断当前环是否为空
            if (head == tail) {
                return null;
            }
            Long datum = data[head];
            head = (head + 1) % size;
            return datum;
        }
    }

    static class Producer {
        private CircleArrayQueue circleArrayQueue;

        public Producer(CircleArrayQueue circleArrayQueue) {
            this.circleArrayQueue = circleArrayQueue;
        }

        public void produce(Long data) throws InterruptedException {
            // 当队列满时,让当前线程休眠1s后再继续写入,直到数据添加成功为止
            while (!circleArrayQueue.add(data)) {
                Thread.sleep(1000L);
            }
        }
    }

    static class Consumer {
        private CircleArrayQueue circleArrayQueue;

        public Consumer(CircleArrayQueue circleArrayQueue) {
            this.circleArrayQueue = circleArrayQueue;
        }

        public void consume() throws InterruptedException {
            // 当队列空时,让当前线程休眠1s后再继续读取
            while (true) {
                Long poll = circleArrayQueue.poll();
                if (poll == null) {
                    Thread.sleep(1000L);
                } else {
                    System.out.println("消费数据:" + poll);
                }
            }
        }
    }


    public static void main(String[] args) {
        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(10);
        new Thread(() -> {
            Producer producer = new Producer(circleArrayQueue);
            long i = 0L;
            while (true) {
                try {
                    producer.produce(i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Consumer consumer = new Consumer(circleArrayQueue);
        try {
            consumer.consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
