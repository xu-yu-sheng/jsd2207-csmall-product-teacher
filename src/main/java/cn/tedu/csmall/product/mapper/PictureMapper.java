package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.vo.PictureListItemVO;
import cn.tedu.csmall.product.pojo.vo.PictureStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理图片数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface PictureMapper {

    /**
     * 插入图片数据
     *
     * @param picture 图片数据
     * @return 受影响的行数
     */
    int insert(Picture picture);

    /**
     * 批量插入图片数据
     *
     * @param pictureList 若干个图片数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Picture> pictureList);

    /**
     * 根据id删除图片数据
     *
     * @param id 图片id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除图片
     *
     * @param ids 需要删除的若干个图片的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新图片数据
     *
     * @param picture 封装了图片的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(Picture picture);

    /**
     * 统计图片数据的数量
     *
     * @return 图片数据的数量
     */
    int count();

    /**
     * 根据相册统计图片数据的数量
     *
     * @param albumId 相册id
     * @return 与此相册关联的图片数据的数量
     */
    int countByAlbumId(Long albumId);

    /**
     * 根据id查询图片标准信息
     *
     * @param id 图片id
     * @return 匹配的图片的标准信息，如果没有匹配的数据，则返回null
     */
    PictureStandardVO getStandardById(Long id);

    /**
     * 查询图片列表
     *
     * @return 图片列表
     */
    List<PictureListItemVO> list();

}
