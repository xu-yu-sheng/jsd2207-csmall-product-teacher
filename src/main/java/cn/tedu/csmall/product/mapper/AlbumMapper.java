package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;

/**
 * 处理相册数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface AlbumMapper {

    /**
     * 插入相册数据
     *
     * @param album 相册数据
     * @return 受影响的行数
     */
    int insert(Album album);

}
