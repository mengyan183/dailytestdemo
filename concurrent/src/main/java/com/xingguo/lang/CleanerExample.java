///*
// * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
// */
//package com.xingguo.lang;
//
//import java.lang.ref.Cleaner;
//
///**
// * CleanerExample
// *
// * @author guoxing
// * @date 2020/3/30 6:42 PM
// * @since
// */
//public class CleanerExample implements AutoCloseable {
//    // A cleaner, preferably one shared within a library
//    private static final Cleaner cleaner = Cleaner.create();
//
//    static class State implements Runnable {
//        State() {
//            // initialize State needed for cleaning action
//        }
//
//        public void run() {
//            // cleanup action accessing State, executed at most once
//
//        }
//    }
//
//    private final State state;
//    private final Cleaner.Cleanable cleanable;
//
//    public CleanerExample() {
//        this.state = new State();
//        this.cleanable = cleaner.register(this, state);
//    }
//
//    public void close() {
//        cleanable.clean();
//        System.out.println("清除对象");
//    }
//
//    public static void main(String[] args) {
//        while (true) {
//            new CleanerExample().state.run();
//        }
//    }
//}
