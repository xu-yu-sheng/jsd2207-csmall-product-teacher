package cn.tedu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SPU详情的标准VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SpuDetailStandardVO implements Serializable {

    /**
     * 数据id
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