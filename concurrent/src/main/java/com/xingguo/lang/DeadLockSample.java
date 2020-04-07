/*
 * Copyright (c) 2020,guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.lang;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * DeadLockExample
 * 死锁案例
 *
 * @author guoxing
 * @date 2020/4/2 3:26 PM
 * @since
 */

public class DeadLockSample extends Thread {
    private String first;
    private String second;

    public DeadLockSample(String name, String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained: " + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained: " + second);
                }
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    /**
     * Full thread dump OpenJDK 64-Bit Server VM (25.222-b10 mixed mode):
     * <p>
     * "Attach Listener" #23 daemon prio=9 os_prio=31 tid=0x00007fa0d0917000 nid=0x400b waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "Thread2" #22 prio=5 os_prio=31 tid=0x00007fa0cf01c800 nid=0x6603 waiting for monitor entry [0x000070000afc5000]
     * java.lang.Thread.State: BLOCKED (on object monitor)
     * at com.xingguo.lang.DeadLockSample.run(DeadLockSample.java:31)
     * - waiting to lock <0x000000076ae0e5e0> (a java.lang.String)
     * - locked <0x000000076ae0e618> (a java.lang.String)
     * <p>
     * "Thread1" #21 prio=5 os_prio=31 tid=0x00007fa0d0061800 nid=0x9a03 waiting for monitor entry [0x000070000aec2000]
     * java.lang.Thread.State: BLOCKED (on object monitor)
     * at com.xingguo.lang.DeadLockSample.run(DeadLockSample.java:31)
     * - waiting to lock <0x000000076ae0e618> (a java.lang.String)
     * - locked <0x000000076ae0e5e0> (a java.lang.String)
     * <p>
     * "Service Thread" #20 daemon prio=9 os_prio=31 tid=0x00007fa0d001f800 nid=0x9c03 runnable [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C1 CompilerThread11" #19 daemon prio=9 os_prio=31 tid=0x00007fa0d3811800 nid=0x6303 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C1 CompilerThread10" #18 daemon prio=9 os_prio=31 tid=0x00007fa0d5808800 nid=0x6103 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C1 CompilerThread9" #17 daemon prio=9 os_prio=31 tid=0x00007fa0d1894000 nid=0x9e03 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C1 CompilerThread8" #16 daemon prio=9 os_prio=31 tid=0x00007fa0d001f000 nid=0x9f03 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread7" #15 daemon prio=9 os_prio=31 tid=0x00007fa0cf01b800 nid=0xa103 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread6" #14 daemon prio=9 os_prio=31 tid=0x00007fa0d001e000 nid=0x5d03 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread5" #13 daemon prio=9 os_prio=31 tid=0x00007fa0d001d800 nid=0x5c03 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread4" #12 daemon prio=9 os_prio=31 tid=0x00007fa0d001c800 nid=0xa303 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread3" #11 daemon prio=9 os_prio=31 tid=0x00007fa0cf01b000 nid=0x5a03 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread2" #10 daemon prio=9 os_prio=31 tid=0x00007fa0d0011000 nid=0x5903 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread1" #9 daemon prio=9 os_prio=31 tid=0x00007fa0d0010000 nid=0xa703 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "C2 CompilerThread0" #8 daemon prio=9 os_prio=31 tid=0x00007fa0d000f800 nid=0xa803 waiting on condition [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "JDWP Command Reader" #7 daemon prio=10 os_prio=31 tid=0x00007fa0d280b800 nid=0x5703 runnable [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "JDWP Event Helper Thread" #6 daemon prio=10 os_prio=31 tid=0x00007fa0d3809000 nid=0x5503 runnable [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "JDWP Transport Listener: dt_socket" #5 daemon prio=10 os_prio=31 tid=0x00007fa0d280b000 nid=0x4607 runnable [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fa0cf808800 nid=0x4503 runnable [0x0000000000000000]
     * java.lang.Thread.State: RUNNABLE
     * <p>
     * "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fa0d4809000 nid=0x3903 in Object.wait() [0x0000700009a83000]
     * java.lang.Thread.State: WAITING (on object monitor)
     * at java.lang.Object.wait(Native Method)
     * - waiting on <0x000000076ab08ed8> (a java.lang.ref.ReferenceQueue$Lock)
     * at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
     * - locked <0x000000076ab08ed8> (a java.lang.ref.ReferenceQueue$Lock)
     * at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
     * at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
     * <p>
     * "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fa0d4808800 nid=0x3703 in Object.wait() [0x0000700009980000]
     * java.lang.Thread.State: WAITING (on object monitor)
     * at java.lang.Object.wait(Native Method)
     * - waiting on <0x000000076ab06c00> (a java.lang.ref.Reference$Lock)
     * at java.lang.Object.wait(Object.java:502)
     * at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
     * - locked <0x000000076ab06c00> (a java.lang.ref.Reference$Lock)
     * at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
     * <p>
     * "main" #1 prio=5 os_prio=31 tid=0x00007fa0cf00d000 nid=0xf03 in Object.wait() [0x0000700008a53000]
     * java.lang.Thread.State: WAITING (on object monitor)
     * at java.lang.Object.wait(Native Method)
     * - waiting on <0x000000076ae0e650> (a com.xingguo.lang.DeadLockSample)
     * at java.lang.Thread.join(Thread.java:1252)
     * - locked <0x000000076ae0e650> (a com.xingguo.lang.DeadLockSample)
     * at java.lang.Thread.join(Thread.java:1326)
     * at com.xingguo.lang.DeadLockSample.main(DeadLockSample.java:46)
     * <p>
     * "VM Thread" os_prio=31 tid=0x00007fa0d3014800 nid=0x4d03 runnable
     * <p>
     * "GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fa0d3009000 nid=0x2207 runnable
     * <p>
     * "GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fa0d3009800 nid=0x1d03 runnable
     * <p>
     * "GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fa0d300a000 nid=0x1f03 runnable
     * <p>
     * "GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fa0d300a800 nid=0x2a03 runnable
     * <p>
     * "GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007fa0d300b800 nid=0x2c03 runnable
     * <p>
     * "GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007fa0d3808800 nid=0x5403 runnable
     * <p>
     * "GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007fa0cf00e000 nid=0x5303 runnable
     * <p>
     * "GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007fa0d300c000 nid=0x2f03 runnable
     * <p>
     * "GC task thread#8 (ParallelGC)" os_prio=31 tid=0x00007fa0d300c800 nid=0x5003 runnable
     * <p>
     * "GC task thread#9 (ParallelGC)" os_prio=31 tid=0x00007fa0d300d000 nid=0x4f03 runnable
     * <p>
     * "GC task thread#10 (ParallelGC)" os_prio=31 tid=0x00007fa0d300e000 nid=0x3203 runnable
     * <p>
     * "GC task thread#11 (ParallelGC)" os_prio=31 tid=0x00007fa0d300e800 nid=0x3403 runnable
     * <p>
     * "GC task thread#12 (ParallelGC)" os_prio=31 tid=0x00007fa0d300f000 nid=0x3603 runnable
     * <p>
     * "VM Periodic Task Thread" os_prio=31 tid=0x00007fa0cf80e800 nid=0x6503 waiting on condition
     * <p>
     * JNI global references: 1400
     * <p>
     * <p>
     * Found one Java-level deadlock:
     * =============================
     * "Thread2":
     * waiting to lock monitor 0x00007fa0d0813948 (object 0x000000076ae0e5e0, a java.lang.String),
     * which is held by "Thread1"
     * "Thread1":
     * waiting to lock monitor 0x00007fa0d0813b58 (object 0x000000076ae0e618, a java.lang.String),
     * which is held by "Thread2"
     * <p>
     * Java stack information for the threads listed above:
     * ===================================================
     * "Thread2":
     * at com.xingguo.lang.DeadLockSample.run(DeadLockSample.java:31)
     * //需要等待的资源对象
     * - waiting to lock <0x000000076ae0e5e0> (a java.lang.String)
     * // 当前持有的资源对象
     * - locked <0x000000076ae0e618> (a java.lang.String)
     * "Thread1":
     * at com.xingguo.lang.DeadLockSample.run(DeadLockSample.java:31)
     * // 需要等待的资源对象
     * - waiting to lock <0x000000076ae0e618> (a java.lang.String)
     * // 当前持有的资源对象
     * - locked <0x000000076ae0e5e0> (a java.lang.String)
     * <p>
     * Found 1 deadlock.
     * 使用jstack pid 命令可以看到发现了死锁
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        checkDeadLock();
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
        DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    // 检查死锁
    public static void checkDeadLock(){
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = () -> {
            long[] threadIds = mbean.findDeadlockedThreads();
            if (threadIds != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(threadIds);
                System.out.println("Detected deadlock threads:");
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println(threadInfo.getThreadName());
                }
            }
        };
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // 稍等5秒，然后每10秒进行一次死锁扫描
        scheduler.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);
    }

}
