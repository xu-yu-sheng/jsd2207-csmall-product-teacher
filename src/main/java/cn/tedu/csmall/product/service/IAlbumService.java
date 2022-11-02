package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;

import java.util.List;

/**
 * 处理相册数据的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IAlbumService {

    /**
     * 添加相册
     *
     * @param albumAddNewDTO 相册数据
     */
    void addNew(AlbumAddNewDTO albumAddNewDTO);

    /**
     * 删除相册
     *
     * @param id 尝试删除的相册的id
     */
    void delete(Long id);

    /**
     * 查询相册列表
     *
     * @return 相册列表
     */
    List<AlbumListItemVO> list();

}
