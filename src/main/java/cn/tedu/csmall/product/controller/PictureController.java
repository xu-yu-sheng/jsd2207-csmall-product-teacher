package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.PictureAddNewDTO;
import cn.tedu.csmall.product.service.IPictureService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理图片数据相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Api(tags = "05. 图片管理模块")
@Slf4j
@RestController
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private IPictureService pictureService;

    public PictureController() {
        log.info("创建控制器对象：PictureController");
    }

    // http://localhost:9080/pictures/add-new
    @ApiOperation("添加图片")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(PictureAddNewDTO pictureAddNewDTO) {
        log.debug("开始处理【添加图片】的请求，参数：{}", pictureAddNewDTO);
        pictureService.addNew(pictureAddNewDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/pictures/9527/delete
    @ApiOperation("删除图片")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "图片id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@PathVariable Long id) {
        log.debug("开始处理【删除图片】的请求，参数：{}", id);
        pictureService.delete(id);
        return JsonResult.ok();
    }

}
