package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SKU数据的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SkuSpecificationStandardVO implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * SKU id
     */
    private Long skuId;

    /**
     * 属性id
     */
    private Long attributeId;

    /**
     * 属性名称
     */
    private String attributeName;

    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 自动补充的计量单位
     */
    private String unit;

    /**
     * 自定义排序序号
     */
    private Integer sort;

}