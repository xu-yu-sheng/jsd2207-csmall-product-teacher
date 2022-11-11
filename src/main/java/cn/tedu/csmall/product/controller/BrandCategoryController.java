package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.service.IBrandCategoryService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理品牌与类别关联相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Api(tags = "03. 品牌与类别关联管理模块")
@Slf4j
@RestController
@RequestMapping("/brand-category")
public class BrandCategoryController {

    @Autowired
    private IBrandCategoryService brandCategoryService;

    public BrandCategoryController() {
        log.info("创建控制器对象：BrandCategoryController");
    }

    // http://localhost:9080/brand-category/add-new
    @ApiOperation("绑定品牌与类别的关联")
    @ApiOperationSupport(order = 100)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "categoryId", value = "类别id", required = true, dataType = "long")
    })
    @PostMapping("/bind")
    public JsonResult<Void> bind(Long brandId, Long categoryId) {
        log.debug("开始处理【绑定品牌与类别的关联】的请求，品牌：{}，类别：{}", brandId, categoryId);
        brandCategoryService.bind(brandId, categoryId);
        return JsonResult.ok();
    }

    // http://localhost:9080/brand-category/9527/unbind
    @ApiOperation("解绑品牌与类别的关联")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "品牌与类别关联的数据id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/unbind")
    public JsonResult<Void> unbind(@PathVariable Long id) {
        log.debug("开始处理【解绑品牌与类别的关联】的请求，参数：{}", id);
        brandCategoryService.unbind(id);
        return JsonResult.ok();
    }

}
