package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.SpuDetail;
import cn.tedu.csmall.product.pojo.vo.SpuDetailListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuDetailStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理SPU详情数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface SpuDetailMapper {

    /**
     * 插入SPU详情数据
     *
     * @param spuDetail SPU详情数据
     * @return 受影响的行数
     */
    int insert(SpuDetail spuDetail);

    /**
     * 批量插入SPU详情数据
     *
     * @param spuDetailList 若干个SPU详情数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<SpuDetail> spuDetailList);

    /**
     * 根据id删除SPU详情数据
     *
     * @param id SPU详情id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除SPU详情
     *
     * @param ids 需要删除的若干个SPU详情的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新SPU详情数据
     *
     * @param spuDetail 封装了SPU详情的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(SpuDetail spuDetail);

    /**
     * 统计SPU详情数据的数量
     *
     * @return SPU详情数据的数量
     */
    int count();

    /**
     * 根据id查询SPU详情标准信息
     *
     * @param id SPU详情id
     * @return 匹配的SPU详情的标准信息，如果没有匹配的数据，则返回null
     */
    SpuDetailStandardVO getStandardById(Long id);

    /**
     * 查询SPU详情列表
     *
     * @return SPU详情列表
     */
    List<SpuDetailListItemVO> list();

}
