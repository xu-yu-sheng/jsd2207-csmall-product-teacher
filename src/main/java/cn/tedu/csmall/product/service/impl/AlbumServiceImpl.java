package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.service.IAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AlbumMapper albumMapper;

    public AlbumServiceImpl() {
        log.debug("创建业务对象：AlbumServiceImpl");
    }

    @Override
    public void addNew(AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理【添加相册】的业务，参数：{}", albumAddNewDTO);
        // 从参数对象中获取相册名称
        String albumName = albumAddNewDTO.getName();
        // 检查相册名称是否已经被占用（相册表中是否已经存在此名称的数据）
        int count = albumMapper.countByName(albumName);
        if (count > 0) {
            // 是：相册名称已经被占用，添加相册失败，抛出异常
            log.debug("相册名称已经被占用，添加相册失败，将抛出异常");
        } else {
            // 否：相册名称没有被占用，则向相册表中插入数据
            log.debug("相册名称没有被占用，将向相册表中插入数据");
        }
    }

}
