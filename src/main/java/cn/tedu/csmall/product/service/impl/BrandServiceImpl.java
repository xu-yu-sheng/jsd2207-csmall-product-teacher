package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.BrandCategoryMapper;
import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.service.IBrandService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理品牌业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

    public BrandServiceImpl() {
        log.info("创建业务对象：BrandServiceImpl");
    }

    @Override
    public void addNew(BrandAddNewDTO brandAddNewDTO) {
        log.debug("开始处理【添加品牌】的业务，参数：{}", brandAddNewDTO);

        // 检查品牌名称是否已经被占用
        String name = brandAddNewDTO.getName();
        int countByName = brandMapper.countByName(name);
        log.debug("尝试添加的品牌名称是：{}，在数据库中此名称的品牌数量为：{}", name, countByName);
        if (countByName > 0) {
            String message = "添加品牌失败，品牌名称【" + brandAddNewDTO.getName() + "】已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建品牌对象，用于插入到数据表
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewDTO, brand);
        // 插入数据
        log.debug("即将向数据库中插入数据：{}", brand);
        int rows = brandMapper.insert(brand);
        if (rows != 1) {
            String message = "添加品牌失败，服务器忙，请稍后再尝试！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除品牌】的业务，参数：{}", id);
        // 调用Mapper对象的getDetailsById()方法执行查询
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERR_NOT_FOUND)
            String message = "删除品牌失败，尝试删除的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查此品牌是否关联了品牌
        {
            int count = brandCategoryMapper.countByBrand(id);
            if (count > 0) {
                String message = "删除品牌失败！当前品牌仍关联了品牌！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 检查此品牌是否关联了SPU
        {
            int count = spuMapper.countByBrand(id);
            if (count > 0) {
                String message = "删除品牌失败！当前品牌仍关联了商品！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 调用Mapper对象的deleteById()执行删除，并获取返回值
        int rows = brandMapper.deleteById(id);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：抛出异常(ERR_DELETE)
            String message = "删除品牌失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(Long id, BrandUpdateDTO brandUpdateDTO) {
        log.debug("开始处理【修改品牌详情】的业务，参数ID：{}, 新数据：{}", id, brandUpdateDTO);
        // 检查名称是否被占用
        {
            int count = brandMapper.countByNameAndNotId(id, brandUpdateDTO.getName());
            if (count > 0) {
                String message = "修改品牌详情失败，品牌名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 调用Mapper对象的getDetailsById()方法执行查询
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERR_NOT_FOUND)
            String message = "修改品牌详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateDTO, brand);
        brand.setId(id);

        // 修改数据
        log.debug("即将修改数据：{}", brand);
        int rows = brandMapper.update(brand);
        if (rows != 1) {
            String message = "修改品牌详情失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    @Override
    public void setEnable(Long id) {
        updateEnableById(id, 1);
    }

    @Override
    public void setDisable(Long id) {
        updateEnableById(id, 0);
    }

    @Override
    public BrandStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询品牌详情】的业务，参数：{}", id);
        BrandStandardVO brand = brandMapper.getStandardById(id);
        if (brand == null) {
            String message = "获取品牌详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return brand;
    }

    @Override
    public List<BrandListItemVO> list() {
        log.debug("开始处理【查询品牌列表】的业务，无参数");
        return brandMapper.list();
    }

    private void updateEnableById(Long id, Integer enable) {
        String[] tips = {"禁用", "启用"};
        log.debug("开始处理【{}品牌】的业务，参数：{}", tips[enable], id);
        // 调用Mapper对象的getDetailsById()方法执行查询
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            String message = tips[enable] + "品牌失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的enable是否为1
        if (queryResult.getEnable().equals(enable)) {
            String message = tips[enable] + "品牌失败，当前品牌已经处于" + tips[enable] + "状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 准备执行更新
        Brand brand = new Brand();
        brand.setId(id);
        brand.setEnable(enable);
        int rows = brandMapper.update(brand);
        if (rows != 1) {
            String message = tips[enable] + "品牌失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

}
