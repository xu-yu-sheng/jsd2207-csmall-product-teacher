package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.service.IBrandService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public JsonResult<Void> addNew(@Validated BrandAddNewDTO brandAddNewDTO) {
        log.debug("开始处理【添加品牌】的请求，参数：{}", brandAddNewDTO);
        brandService.addNew(brandAddNewDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/9527/delete
    @ApiOperation("根据id删除品牌")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@PathVariable Long id) {
        log.debug("开始处理【根据id删除品牌】的请求，参数：{}", id);
        brandService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/9527/update
    @ApiOperation("修改品牌详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/update")
    public JsonResult<Void> updateById(@PathVariable Long id, BrandUpdateDTO brandUpdateDTO) {
        log.debug("开始处理【修改品牌详情】的请求，参数ID：{}, 新数据：{}", id, brandUpdateDTO);
        brandService.updateInfoById(id, brandUpdateDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/9527/enable
    @ApiOperation("启用品牌")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/enable")
    public JsonResult<Void> setEnable(@PathVariable Long id) {
        log.debug("开始处理【启用品牌】的请求，参数：{}", id);
        brandService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/9527/disable
    @ApiOperation("禁用品牌")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/disable")
    public JsonResult<Void> setDisable(@PathVariable Long id) {
        log.debug("开始处理【禁用品牌】的请求，参数：{}", id);
        brandService.setDisable(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/brands/9527
    @ApiOperation("根据id查询品牌详情")
    @ApiOperationSupport(order = 400)
    @GetMapping("/{id:[0-9]+}")
    public JsonResult<BrandStandardVO> getStandardById(@PathVariable Long id) {
        log.debug("开始处理【根据id查询品牌详情】的请求，参数：{}", id);
        BrandStandardVO brand = brandService.getStandardById(id);
        return JsonResult.ok(brand);
    }

    // http://localhost:9080/brands
    @ApiOperation("查询品牌列表")
    @ApiOperationSupport(order = 410)
    @GetMapping("")
    public JsonResult<List<BrandListItemVO>> list() {
        log.debug("开始处理【查询品牌列表】的请求，无参数");
        List<BrandListItemVO> list = brandService.list();
        return JsonResult.ok(list);
    }

    // http://localhost:9080/brands/cache/rebuild
    @ApiOperation("重建品牌缓存")
    @ApiOperationSupport(order = 600)
    @PostMapping("/cache/rebuild")
    public JsonResult<Void> rebuildCache() {
        log.debug("开始处理【重建品牌缓存】的请求，无参数");
        brandService.rebuildCache();
        return JsonResult.ok();
    }

}
