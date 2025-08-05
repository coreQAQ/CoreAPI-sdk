package com.core.coreapisdk.model;

import lombok.Data;

/**
 * IP归属地精简信息
 */
@Data
public class IpDetail {

    /**
     * 客户端 IP
     */
    private String ip;

    /**
     * 国家 ISO 代码，如 CN、US
     */
    private String countryCode;

    /**
     * 国家中文名
     */
    private String countryName;

    /**
     * 省份/州代码，例如 JS（江苏）
     */
    private String provinceCode;

    /**
     * 省份中文名
     */
    private String provinceName;

    /**
     * 城市中文名
     */
    private String cityName;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 时区标识
     */
    private String timeZone;



}
