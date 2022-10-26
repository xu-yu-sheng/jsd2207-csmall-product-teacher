package cn.tedu.csmall.product.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SPU详情
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SpuDetail implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * SPU id
     */
    private Long spuId;

    /**
     * SPU详情，应该使用HTML富文本，通常内容是若干张图片
     */
    private String detail;

    /**
     * 数据创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    private LocalDateTime gmtModified;

}