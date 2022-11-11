package cn.tedu.csmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新相册基本信息的DTO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class AlbumUpdateDTO implements Serializable {

    /**
     * 相册名称
     */
    @ApiModelProperty(value = "相册名称", required = true)
    private String name;

    /**
     * 相册简介
     */
    @ApiModelProperty(value = "相册简介", required = true)
    private String description;

    /**
     * 自定义排序序号
     */
    @ApiModelProperty(value = "排序序号", required = true)
    private Integer sort;

}