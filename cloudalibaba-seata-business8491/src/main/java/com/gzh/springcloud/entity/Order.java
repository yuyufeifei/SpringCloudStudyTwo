package com.gzh.springcloud.entity;

import java.io.Serializable;

/**
 * @author GZH
 * @date 2022-03-18
 */
public class Order implements Serializable {
    public long id;

    public String userId;

    public String commodityCode;

    public int count;

    public int money;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", count=" + count +
                ", money=" + money +
                '}';
    }
}
