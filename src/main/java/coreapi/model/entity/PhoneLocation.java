package coreapi.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 手机号归属地
 */
@Data
public class PhoneLocation implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 号段前缀
     */
    private String pref;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商类型名称
     */
    private String isp;

    /**
     * 运营商类型（1：移动 2：联通 3：电信 4：广电 5：工信）
     */
    private Integer ispType;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 城市区号
     */
    private String cityCode;

    /**
     * 行政区划编码
     */
    private String areaCode;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

}