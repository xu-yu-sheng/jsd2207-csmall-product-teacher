package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.service.ICategoryService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理类别相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/categories")
@Api(tags = "01. 类别管理模块")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    public CategoryController() {
        log.info("创建控制器对象：CategoryController");
    }

    // http://localhost:9080/categories/add-new
    @ApiOperation("添加类别")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(CategoryAddNewDTO categoryAddNewDTO) {
        log.debug("开始处理【添加类别】的请求，参数：{}", categoryAddNewDTO);
        categoryService.addNew(categoryAddNewDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/delete
    @ApiOperation("根据id删除类别")
    @ApiOperationSupport(order = 200)
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@PathVariable Long id) {
        log.debug("开始处理【根据id删除类别】的请求，参数：{}", id);
        categoryService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/update
    @ApiOperation("修改类别详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/update")
    public JsonResult<Void> updateById(@PathVariable Long id, CategoryUpdateDTO categoryUpdateDTO) {
        log.debug("开始处理【修改类别详情】的请求，参数ID：{}, 新数据：{}", id, categoryUpdateDTO);
        categoryService.updateInfoById(id, categoryUpdateDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/enable
    @ApiOperation("启用类别")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/enable")
    public JsonResult<Void> setEnable(@PathVariable Long id) {
        log.debug("开始处理【启用类别】的请求，参数：{}", id);
        categoryService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/disable
    @ApiOperation("禁用类别")
    @ApiOperationSupport(order = 311)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/disable")
    public JsonResult<Void> setDisable(@PathVariable Long id) {
        log.debug("开始处理【禁用类别】的请求，参数：{}", id);
        categoryService.setDisable(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/display
    @ApiOperation("显示类别")
    @ApiOperationSupport(order = 312)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/display")
    public JsonResult<Void> setDisplay(@PathVariable Long id) {
        log.debug("开始处理【显示类别】的请求，参数：{}", id);
        categoryService.setDisplay(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527/hidden
    @ApiOperation("隐藏类别")
    @ApiOperationSupport(order = 313)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/hidden")
    public JsonResult<Void> setHidden(@PathVariable Long id) {
        log.debug("开始处理【隐藏类别】的请求，参数：{}", id);
        categoryService.setHidden(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/categories/9527
    @ApiOperation("根据id查询类别详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    @GetMapping("/{id:[0-9]+}")
    public JsonResult<CategoryStandardVO> getStandardById(@PathVariable Long id) {
        log.debug("开始处理【根据id查询类别详情】的请求，参数：{}", id);
        CategoryStandardVO category = categoryService.getStandardById(id);
        return JsonResult.ok(category);
    }

    // http://localhost:9080/categories
    @ApiOperation("查询类别列表")
    @ApiOperationSupport(order = 410)
    @GetMapping("")
    public JsonResult<List<CategoryListItemVO>> list() {
        log.debug("开始处理【查询类别列表】的请求，无参数");
        List<CategoryListItemVO> list = categoryService.list();
        return JsonResult.ok(list);
    }

}
