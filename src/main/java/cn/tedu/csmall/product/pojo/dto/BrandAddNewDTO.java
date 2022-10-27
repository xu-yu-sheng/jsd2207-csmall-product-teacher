package cn.tedu.csmall.product.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加品牌的DTO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class BrandAddNewDTO implements Serializable {

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌名称的拼音
     */
    private String pinyin;

    /**
     * 品牌logo的URL
     */
    private String logo;

    /**
     * 品牌简介
     */
    private String description;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 自定义排序序号
     */
    private Integer sort;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

}
