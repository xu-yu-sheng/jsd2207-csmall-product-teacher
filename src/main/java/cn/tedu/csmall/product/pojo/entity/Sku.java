package cn.tedu.csmall.product.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SKU（Stock Keeping Unit）
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class Sku implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * SPU id
     */
    private Long spuId;

    /**
     * 标题
     */
    private String title;

    /**
     * 条型码
     */
    private String barCode;

    /**
     * 属性模板id
     */
    private Long attributeTemplateId;

    /**
     * 全部属性，使用JSON格式表示（冗余）
     */
    private String specifications;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 组图URLs，使用JSON格式表示
     */
    private String pictures;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 当前库存
     */
    private Integer stock;

    /**
     * 库存预警阈值
     */
    private Integer stockThreshold;

    /**
     * 销量（冗余）
     */
    private Integer sales;

    /**
     * 买家评论数量总和（冗余）
     */
    private Integer commentCount;

    /**
     * 买家好评数量总和（冗余）
     */
    private Integer positiveCommentCount;

    /**
     * 自定义排序序号
     */
    private Integer sort;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}