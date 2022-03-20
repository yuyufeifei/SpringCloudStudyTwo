package com.gzh.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author GZH
 * @date 2022-03-17
 */
public class MyBlockHandler {

    public static Object handlerException1(BlockException be) {
        return "全局block handler 1";
    }

    public static Object handlerException2(BlockException be) {
        return "全局block handler 2";
    }

}
