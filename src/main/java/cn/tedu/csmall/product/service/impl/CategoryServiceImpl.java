package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.service.ICategoryService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理类别业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl() {
        log.info("创建业务对象：CategoryServiceImpl");
    }

    @Override
    public void addNew(CategoryAddNewDTO categoryAddNewDTO) {
        log.debug("开始处理【添加类别】的业务，参数：{}", categoryAddNewDTO);

        // 调用Mapper对象的【根据名称统计数量】方法进行统计
        String name = categoryAddNewDTO.getName();
        int count = categoryMapper.countByName(name);
        log.debug("根据名称【{}】统计数量：{}", name, count);
        // 判断统计结果是否大于0
        if (count > 0) {
            // 是：名称已经被占用，抛出异常（CONFLICT）
            String message = "添加类别失败，尝试添加的类别名称【" + name + "】已经存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建Category实体类的对象
        Category category = new Category();
        // 将参数DTO的各属性值复制到Category实体类对象中
        BeanUtils.copyProperties(categoryAddNewDTO, category);
        // 调用Mapper对象的方法，将数据插入到数据库，并获取返回值
        log.debug("准备向数据库中写入类别数据：{}", category);
        categoryMapper.insert(category);
    }

}
