package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.service.IAlbumService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处理相册相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
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
    public String addNew(@RequestBody AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
        albumService.addNew(albumAddNewDTO);
        log.debug("添加相册成功！");
        return "添加相册成功！";
    }

    // http://localhost:8080/albums/9527/delete
    @ApiOperation("根据id删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    @PostMapping("/{id:[0-9]+}/delete")
    public String delete(@PathVariable Long id) {
        String message = "尝试删除id值为【" + id + "】的相册";
        log.debug(message);
        return message;
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
