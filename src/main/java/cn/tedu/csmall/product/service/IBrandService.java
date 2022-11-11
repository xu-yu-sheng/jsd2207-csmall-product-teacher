package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;

import java.util.List;

/**
 * 品牌业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IBrandService {

    /**
     * 添加品牌
     *
     * @param brandAddNewDTO 品牌数据
     */
    void addNew(BrandAddNewDTO brandAddNewDTO);

    /**
     * 根据品牌id，删除品牌数据
     *
     * @param id 尝试删除的品牌的id
     */
    void delete(Long id);

    /**
     * 根据品牌id，修改品牌详情
     *
     * @param id             品牌id
     * @param brandUpdateDTO 新的品牌数据
     */
    void updateInfoById(Long id, BrandUpdateDTO brandUpdateDTO);

    /**
     * 启用品牌
     *
     * @param id 尝试启用的品牌的id
     */
    void setEnable(Long id);

    /**
     * 禁用品牌
     *
     * @param id 尝试禁用的品牌的id
     */
    void setDisable(Long id);

    /**
     * 根据id获取品牌详情
     *
     * @param id 品牌id
     * @return 返回与id匹配的品牌数据详情，如果没有匹配的数据，则返回null
     */
    BrandStandardVO getStandardById(Long id);

    /**
     * 查询品牌列表
     *
     * @return 品牌列表，如果没有匹配的品牌，将返回长度为0的列表
     */
    List<BrandListItemVO> list();

}
