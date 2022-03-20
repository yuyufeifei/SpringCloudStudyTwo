package com.gzh.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg) {
        this(code, msg, null);
    }
}
