package com.core.coreapisdk.model;

import lombok.Data;

@Data
public class BaseResponse<T> {

    /**
     * 业务状态码
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

}
