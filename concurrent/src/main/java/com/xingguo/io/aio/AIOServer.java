///*
// * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
// */
//package com.xingguo.io.aio;
//
//import com.xingguo.io.nio.NIOServer;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//import java.nio.channels.*;
//import java.nio.charset.Charset;
//import java.util.Iterator;
//import java.util.Set;
//
///**
// * AIOServer
// *
// * @author guoxing
// * @date 2020/4/2 8:31 AM
// * @since
// */
//public class AIOServer implements Runnable {
//    @Override
//    public void run() {
//        try (AsynchronousServerSocketChannel serverSock = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(InetAddress.getLocalHost(), 8888));) {// 创建Selector和Channel
//            while (true) {
//                serverSock.accept(serverSock, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
//                    //为异步操作指定CompletionHandler回调函数
//                    @Override
//                    public void completed(AsynchronousSocketChannel sockChannel, AsynchronousServerSocketChannel serverSock) {
//                        serverSock.accept(serverSock, this);
//                        // 另外一个 write（sock，CompletionHandler{}）
//                        sayHelloWorld(sockChannel, Charset.defaultCharset().encode
//                                ("Hello World!"));
//                    }
//
//                    @Override
//                    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
//
//                    }
//                });
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sayHelloWorld(AsynchronousSocketChannel server, ByteBuffer byteBuffer) {
//        server.write(byteBuffer);
//    }
//
//    public static void main(String[] args) throws IOException {
//        AIOServer server = new AIOServer();
//        server.run();
//        try (Socket client = new Socket(InetAddress.getLocalHost(), 8888)) {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            bufferedReader.lines().forEach(System.out::println);
//        }
//    }
//}
