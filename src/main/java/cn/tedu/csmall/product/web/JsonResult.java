package cn.tedu.csmall.product.web;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JsonResult<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer state;
    /**
     * 操作失败时的描述文本
     */
    @ApiModelProperty("操作失败时的描述文本")
    private String message;
    /**
     * 操作成功时响应的数据
     */
    @ApiModelProperty("操作成功时响应的数据")
    private T data;

    public static JsonResult ok() {
        return ok(null);
    }

    public static <T> JsonResult ok(T data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = ServiceCode.OK.getValue();
        jsonResult.data = data;
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }

}