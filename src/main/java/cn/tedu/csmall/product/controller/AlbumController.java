package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.service.IAlbumService;
import cn.tedu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 处理相册相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/albums")
@Api(tags = "04. 相册管理模块")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    public AlbumController() {
        log.debug("创建控制器对象：AlbumController");
    }

    // http://localhost:8080/albums/add-new?name=相册001&description=相册001的简介&sort=199
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Valid AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
        albumService.addNew(albumAddNewDTO);
        log.debug("添加相册成功！");
        return JsonResult.ok();
    }

    // http://localhost:8080/albums/9527/delete
    @ApiOperation("根据id删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult<Void> delete(@Range(min = 1, message = "删除相册失败，尝试删除的相册的ID无效！")
                                   @PathVariable Long id) {
        log.debug("开始处理【根据id删除相册】的请求，参数：{}", id);
        albumService.delete(id);
        return JsonResult.ok();
    }

    // http://localhost:9080/albums/9527/update
    @ApiOperation("修改相册详情")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/update")
    public JsonResult<Void> updateById(@PathVariable Long id, AlbumUpdateDTO albumUpdateDTO) {
        log.debug("开始处理【修改相册详情】的请求，参数ID：{}, 新数据：{}", id, albumUpdateDTO);
        albumService.updateInfoById(id, albumUpdateDTO);
        return JsonResult.ok();
    }

    // http://localhost:9080/albums/9527
    @ApiOperation("根据id查询相册详情")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    @GetMapping("/{id:[0-9]+}")
    public JsonResult<AlbumStandardVO> getStandardById(@PathVariable Long id) {
        log.debug("开始处理【根据id查询相册详情】的请求，参数：{}", id);
        AlbumStandardVO album = albumService.getStandardById(id);
        return JsonResult.ok(album);
    }

    // http://localhost:8080/albums
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @GetMapping("")
    public JsonResult<List<AlbumListItemVO>> list() {
        log.debug("开始处理【查询相册列表】的请求，无参数");
        List<AlbumListItemVO> list = albumService.list();
        return JsonResult.ok(list);
    }

    // http://localhost:8080/albums/hello/delete
    @ApiOperation("【已过期】根据名称删除相册")
    @ApiOperationSupport(order = 901)
    @PostMapping("/{name:[a-z]+}/delete")
    public String delete2(@PathVariable String name) {
        String message = "尝试删除名称值为【" + name + "】的相册";
        log.debug(message);
        return message;
    }

    // http://localhost:8080/albums/test/delete
    @ApiOperation("【已过期】测试删除相册")
    @ApiOperationSupport(order = 902)
    @PostMapping("/test/delete")
    public String delete3() {
        String message = "尝试测试删除相册";
        log.debug(message);
        return message;
    }

}
