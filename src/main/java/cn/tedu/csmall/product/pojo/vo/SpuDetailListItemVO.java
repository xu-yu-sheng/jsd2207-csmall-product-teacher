package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SPU详情的列表项VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SpuDetailListItemVO implements Serializable {

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

}