package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.service.ICategoryService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String addNew(CategoryAddNewDTO categoryAddNewDTO) {
        log.debug("开始处理【添加类别】的请求，参数：{}", categoryAddNewDTO);
        categoryService.addNew(categoryAddNewDTO);
        log.debug("添加相册成功！");
        return "添加相册成功！";
    }

}
