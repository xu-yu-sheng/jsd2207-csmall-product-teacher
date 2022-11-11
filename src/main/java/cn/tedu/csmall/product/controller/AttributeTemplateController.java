package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateUpdateInfoDTO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
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
    public JsonResult<Void> addNew(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO) {
        log.debug("开始处理【添加属性模板】的请求，参数：{}", attributeTemplateAddNewDTO);
        attributeTemplateService.addNew(attributeTemplateAddNewDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/attribute-templates/9527/delete
    @ApiOperation("根据id删除属性模板")
    @ApiOperationSupport(order = 200)
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@PathVariable Long id) {
        log.debug("开始处理【根据id删除属性模板】的请求，参数：{}", id);
        attributeTemplateService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/attribute-templates/9527/update
    @ApiOperation("修改属性模板详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id", value = "属性模板id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/update")
    public JsonResult<Void> updateInfoById(@PathVariable Long id, AttributeTemplateUpdateInfoDTO attributeTemplateUpdateInfoDTO) {
        log.debug("开始处理【修改属性模板详情】的请求，参数ID：{}, 新数据：{}", id, attributeTemplateUpdateInfoDTO);
        attributeTemplateService.updateInfoById(id, attributeTemplateUpdateInfoDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/attribute-templates/9527
    @ApiOperation("根据id查询属性模板详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParam(name = "id", value = "属性模板id", required = true, dataType = "long")
    @GetMapping("/{id:[0-9]+}")
    public JsonResult<AttributeTemplateStandardVO> getStandardById(@PathVariable Long id) {
        log.debug("开始处理【根据id查询属性模板详情】的请求，参数：{}", id);
        AttributeTemplateStandardVO attributeTemplate = attributeTemplateService.getStandardById(id);
        return JsonResult.ok(attributeTemplate);
    }

    // http://localhost:9080/attribute-templates
    @ApiOperation("查询属性模板列表")
    @ApiOperationSupport(order = 410)
    @GetMapping("")
    public JsonResult<List<AttributeTemplateListItemVO>> list() {
        log.debug("开始处理【查询属性模板列表】的请求，无参数");
        List<AttributeTemplateListItemVO> list = attributeTemplateService.list();
        return JsonResult.ok(list);
    }

}
