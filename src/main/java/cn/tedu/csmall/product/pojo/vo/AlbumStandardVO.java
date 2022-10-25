package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

/**
 * 相册数据的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AlbumStandardVO {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册简介
     */
    private String description;

    /**
     * 自定义排序序号
     */
    private Integer sort;

}
