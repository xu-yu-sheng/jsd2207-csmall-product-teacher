package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.service.IAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 处理相册数据的业务实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {

    public AlbumServiceImpl() {
        log.debug("创建业务对象：AlbumServiceImpl");
    }

}
