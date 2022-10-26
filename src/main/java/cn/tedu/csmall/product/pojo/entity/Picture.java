package cn.tedu.csmall.product.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 图片
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class Picture implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 相册id
     */
    private Long albumId;

    /**
     * 图片url
     */
    private String url;

    /**
     * 是否为封面图片，1=是，0=否
     */
    private Integer isCover;

    /**
     * 图片简介
     */
    private String description;

    /**
     * 图片宽度，单位：px
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

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}