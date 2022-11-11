package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.BrandCategoryMapper;
import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import cn.tedu.csmall.product.service.IBrandCategoryService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理品牌与类别关联数据业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class BrandCategoryServiceImpl implements IBrandCategoryService {

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public BrandCategoryServiceImpl() {
        log.info("创建业务对象：BrandCategoryServiceImpl");
    }

    @Override
    public void bind(Long brandId, Long categoryId) {
        log.debug("开始处理【绑定品牌与类别的关联】的业务，品牌：{}，类别：{}", brandId, categoryId);

        // 检查品牌是否存在
        {
            Object queryResult = brandMapper.getStandardById(brandId);
            if (queryResult == null) {
                String message = "绑定品牌与类别的关联失败！品牌不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
            }
        }

        // 检查类别是否存在
        {
            Object queryResult = categoryMapper.getStandardById(categoryId);
            if (queryResult == null) {
                String message = "绑定品牌与类别的关联失败！类别不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
            }
        }

        // 检查关联是否存在
        {
            int count = brandCategoryMapper.countByBrandAndCategory(brandId, categoryId);
            if (count > 0) {
                String message = "绑定品牌与类别的关联失败！此品牌与类别的绑定已存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 创建对象
        BrandCategory brandCategory = new BrandCategory();
        // 复制品牌与类别的绑定
        brandCategory.setBrandId(brandId);
        brandCategory.setCategoryId(categoryId);
        // 执行插入数据
        int rows = brandCategoryMapper.insert(brandCategory);
        if (rows != 1) {
            String message = "绑定品牌与类别的关联失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void unbind(Long id) {
        log.debug("开始处理【解绑品牌与类别的关联】的业务，参数：{}", id);
        // 检查尝试删除的品牌与类别的绑定是否存在
        Object queryResult = brandCategoryMapper.getStandardById(id);
        if (queryResult == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "解绑品牌与类别的关联失败，尝试访问的数据不存在！");
        }

        // 执行删除
        log.debug("即使删除id为{}的品牌与类别的绑定……", id);
        int rows = brandCategoryMapper.deleteById(id);
        if (rows != 1) {
            throw new ServiceException(ServiceCode.ERR_DELETE, "解绑品牌与类别的关联失败，服务器忙，请稍后再次尝试！");
        }
        log.debug("解绑品牌与类别的关联完成！");
    }

}
