package cn.tedu.csmall.product.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加图片的DTO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class PictureAddNewDTO implements Serializable {

    /**
     * 所属相册id
     */
    private Long albumId;

    /**
     * URL
     */
    private String url;

    /**
     * 简介
     */
    private String description;

    /**
     * 宽度，单位：px
     */
    private Integer width;

    /**
     * 图片高度，单位：px
     */
    private Integer height;

    /**
     * 自定义排序序号
     */
    private Integer sort;

}
