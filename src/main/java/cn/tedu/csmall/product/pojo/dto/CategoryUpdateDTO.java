package cn.tedu.csmall.product.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新类别基本信息的DTO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class CategoryUpdateDTO implements Serializable {

    /**
     * 类别名称
     */
    private String name;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 自定义排序序号
     */
    private Integer sort;

    /**
     * 图标图片的URL
     */
    private String icon;

}