package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理属性模板相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/attribute-templates")
@Api(tags = "06. 属性模板管理模块")
public class AttributeTemplateController {

    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    public AttributeTemplateController() {
        log.info("创建控制器对象：AttributeTemplateController");
    }

    // http://localhost:9080/attribute-templates/add-new
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public String addNew(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO) {
        log.debug("开始处理【添加属性模板】的请求：{}", attributeTemplateAddNewDTO);
        attributeTemplateService.addNew(attributeTemplateAddNewDTO);
        log.debug("添加属性模板成功！");
        return "添加属性模板成功！";
    }

}
