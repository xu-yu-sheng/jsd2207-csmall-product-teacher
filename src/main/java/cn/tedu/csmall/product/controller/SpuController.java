package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.csmall.product.service.ISpuService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 处理SPU相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/spu")
@Api(tags = "08. SPU管理模块")
public class SpuController {

    @Autowired
    private ISpuService spuService;

    public SpuController() {
        log.debug("创建控制器对象：SpuController");
    }

    // http://localhost:8080/spu/add-new?name=SPU001
    @ApiOperation("添加SPU")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Valid SpuAddNewDTO spuAddNewDTO) {
        log.debug("开始处理【添加SPU】的请求，参数：{}", spuAddNewDTO);
        spuService.addNew(spuAddNewDTO);
        log.debug("添加SPU成功！");
        return JsonResult.ok();
    }

}
