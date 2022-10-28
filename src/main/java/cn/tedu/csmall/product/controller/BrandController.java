package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.csmall.product.service.IBrandService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
        log.debug("添加相册成功！");
        return "添加相册成功！";
    }

}
