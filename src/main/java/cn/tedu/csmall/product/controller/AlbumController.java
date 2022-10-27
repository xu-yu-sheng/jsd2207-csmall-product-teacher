package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.service.IAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理相册相关请求的控制器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RequestMapping("/albums")
@RestController
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    public AlbumController() {
        log.debug("创建控制器对象：AlbumController");
    }

    // http://localhost:8080/albums/add-new?name=相册001&description=相册001的简介&sort=199
    @PostMapping("/add-new")
    public String addNew(AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
        albumService.addNew(albumAddNewDTO);
        log.debug("添加数据成功！");
        return "添加相册成功！";
    }

    // http://localhost:8080/albums/9527/delete
    @PostMapping("/{id:[0-9]+}/delete")
    public String delete(@PathVariable Long id) {
        String message = "尝试删除id值为【" + id + "】的相册";
        log.debug(message);
        return message;
    }

    // http://localhost:8080/albums/hello/delete
    @PostMapping("/{name:[a-z]+}/delete")
    public String delete2(@PathVariable String name) {
        String message = "尝试删除名称值为【" + name + "】的相册";
        log.debug(message);
        return message;
    }

    // http://localhost:8080/albums/test/delete
    @PostMapping("/test/delete")
    public String delete3() {
        String message = "尝试测试删除相册";
        log.debug(message);
        return message;
    }

}
