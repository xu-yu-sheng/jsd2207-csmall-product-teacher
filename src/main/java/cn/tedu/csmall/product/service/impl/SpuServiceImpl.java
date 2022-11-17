package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.mapper.*;
import cn.tedu.csmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.csmall.product.service.ISpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Spu业务实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class SpuServiceImpl implements ISpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AlbumMapper albumMapper;

    public SpuServiceImpl() {
        log.info("创建业务对象：SpuServiceImpl");
    }

    @Override
    public void addNew(SpuAddNewDTO spuAddNewDTO) {
        // 根据相册id查询详情，检查是否存在
        // 根据品牌id查询详情，检查是否存在
        // 检查品牌是否已经被禁用
        // 根据类别id查询详情，检查是否存在
        // 检查类别是否已经被禁用
        // 检查类别是否没有子级

        // 创建Spu对象，并将参数对象的属性复制过来
        // 补全Spu对象的属性：ID >>> IdUtils.getId()
        // 补全Spu对象的属性：brandName >>> 前序检查时的查询结果
        // 补全Spu对象的属性：categoryName >>> 前序检查时的查询结果
        // 补全Spu对象的属性：sales / commentCount / positiveCommentCount >>> 0
        // 补全Spu对象的属性：isDeleted / isPublished / isChecked >>> 0
        // 补全Spu对象的属性：isNewArrival / isRecommend >>> 1（可自行决定）
        // 插入Spu数据，获取返回结果并检查

        // 创建SpuDetail对象
        // 补全SpuDetail对象的属性：spuId >>> 以上Spu使用的ID
        // 补全SpuDetail对象的属性：detail >>> 来自参数对象
        // 插入SpuDetail数据，获取返回结果并检查
    }

}
