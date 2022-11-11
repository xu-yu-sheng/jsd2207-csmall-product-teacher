package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;

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
     * 根据相册id，修改相册详情
     *
     * @param id             相册id
     * @param albumUpdateDTO 新的相册数据
     */
    void updateInfoById(Long id, AlbumUpdateDTO albumUpdateDTO);

    /**
     * 根据id获取相册的标准信息
     *
     * @param id 相册id
     * @return 返回匹配的相册的标准信息，如果没有匹配的数据，将返回null
     */
    AlbumStandardVO getStandardById(Long id);

    /**
     * 查询相册列表
     *
     * @return 相册列表
     */
    List<AlbumListItemVO> list();

}
