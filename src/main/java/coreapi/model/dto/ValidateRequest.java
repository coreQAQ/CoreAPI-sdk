package coreapi.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据校验请求
 */
@Data
public class ValidateRequest implements Serializable {

    /**
     * 待校验的字符串列表
     */
    private List<String> values;

    /**
     * 校验类型。可选值：
     * email(邮箱), mobile(手机号), chinese(中文), idcard(身份证号), url(URL),
     * mac(MAC地址), ipv4(IPv4地址), ipv6(IPv6地址), zipcode(邮政编码),
     * birthday(生日), plate(车牌号)
     */
    private String type;

    private static final long serialVersionUID = 1L;

}