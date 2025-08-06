package coreapi.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据校验结果
 */
@Data
public class ValidateResult implements Serializable {

    /**
     * 原始字符串列表
     */
    private List<String> values;

    /**
     * 校验类型
     */
    private String type;

    /**
     * 每一项是否校验通过
     */
    private List<Boolean> isValid;

    private static final long serialVersionUID = 1L;

}