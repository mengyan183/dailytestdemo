/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.algorithm.stack;

/**
 * BrowserStackDemo
 * 使用栈实现浏览器快进后退功能
 *
 * @author guoxing
 * @date 2020/4/18 8:48 PM
 * @since
 */
public class BrowserStackDemo {
    // 过去+当前
    private LinkedStack<String> past = new LinkedStack<String>();
    // 未来
    private LinkedStack<String> future = new LinkedStack<String>();

    /**
     * 后退
     *
     * @return
     */
    public String back() {
        // 当前页面
        String pop = past.pop();
        if (pop == null) {
            return null;
        }
        future.in(pop);
        String currentPage = past.pop();
        past.in(currentPage);
        return currentPage;
    }

    /**
     * 前进
     *
     * @return
     */
    public String forward() {
        String pop = future.pop();
        if (pop == null) {
            return null;
        }
        past.in(pop);
        return pop;
    }

    /**
     * 打开一个新页面
     *
     * @param newPage
     */
    public void createNewPage(String newPage) {
        past.in(newPage);
        // 也可以使用创建一个新的栈清空数据
        future = new LinkedStack<String>();
        // 清空前进栈中的全部记录,打开新的页面后,就不可以使用前进功能
        while (future.pop() != null) {
        }
    }
}
