package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.csmall.product.service.IBrandService;
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
 * 处理品牌相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/brands")
@Api(tags = "02. 品牌管理模块")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    public BrandController() {
        log.info("创建控制器对象：BrandController");
    }

    // http://localhost:9080/brands/add-new
    @ApiOperation("添加品牌")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public String addNew(BrandAddNewDTO brandAddNewDTO) {
        log.debug("开始处理【添加品牌】的请求：{}", brandAddNewDTO);
        brandService.addNew(brandAddNewDTO);
        return "添加相册成功！";
    }

    // http://localhost:9080/brands/9527/delete
    @ApiOperation("删除品牌")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@PathVariable Long id) {
        log.debug("开始处理【删除品牌】的请求，参数：{}", id);
        brandService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/3/enable
    @ApiOperation("启用品牌")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/enable")
    public JsonResult<Void> setEnable(@PathVariable Long id) {
        log.debug("开始处理【启用品牌】的请求，参数：{}", id);
        brandService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/3/disable
    @ApiOperation("禁用品牌")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/disable")
    public JsonResult<Void> setDisable(@PathVariable Long id) {
        log.debug("开始处理【禁用品牌】的请求，参数：{}", id);
        brandService.setDisable(id);
        return JsonResult.ok();
    }

}
