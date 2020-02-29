/*
 * Copyright (c) 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.concurrent.livelock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * LiveLock
 *
 * @author guoxing
 * @date 2020/2/28 9:43 AM
 * @since
 */
public class LiveLock {

    private static class Spoon {
        private Diner owner;

        public Spoon(Diner owner) {
            this.owner = owner;
        }

        public synchronized void use() {
            System.out.println(owner.name + " has eaten!");
        }
    }

    private static class Diner {
        private String name;
        private boolean isHungry;

        public Diner(String name) {
            this.name = name;
            this.isHungry = true;
        }

        /**
         * Spoon作为共享变量
         * <p>
         * 当man第一次进入后,第一个if为false ,第二个if为true,spoon变为woman , 继续循环
         * 当woman第一次进入后,第一个if为true,所以woman sleep 1ms;继续循环
         * <p>
         * =====================================================================
         * 当man第二次进入时,第一个if为true,所以当前man sleep 1ms;继续循环
         * 当woman第二次进入时,第一个if为false ,第二个if为true,spoon变为man , 继续循环
         * <p>
         * ======================================================================
         * <p>
         * 之后就和1,2相同进入交替循环状态,因为没有竞争资源问题,而由于相互谦让资源导致的;而且第二个if永远为true,所以不可能执行到下一步
         *
         * @param spoon
         * @param spouse
         */
        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                if (spoon.owner != this) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                // 在if判断中增加随机判断或增加重试补偿机制
                if (spouse.isHungry && new Random().nextInt(10) < 7) {
//                    if (spouse.isHungry) {
                        System.out.println(Thread.currentThread().getName() + " : " + name + " : " + spouse.name + " first!");
                        spoon.owner = spouse;
                        continue;
                    }

                    spoon.use();
                    isHungry = false;
                    System.out.println(name + " : finished!");
                    spoon.owner = spouse;
                }
            }
        }

        public static void main(String[] args) {
            Diner man = new Diner("Man");
            Diner woman = new Diner("Women");

            Spoon spoon = new Spoon(man);

            Thread manThread = new Thread(() -> {
                man.eatWith(spoon, woman);
            });
            Thread womanThread = new Thread(() -> {
                woman.eatWith(spoon, man);
            });
            manThread.start();
            womanThread.start();
        }

    }
