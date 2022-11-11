package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.BrandCategoryMapper;
import cn.tedu.csmall.product.mapper.CategoryAttributeTemplateMapper;
import cn.tedu.csmall.product.mapper.CategoryMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.tedu.csmall.product.service.ICategoryService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private BrandCategoryMapper brandCategoryMapper;
    @Autowired
    private CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;
    @Autowired
    private SpuMapper spuMapper;

    public CategoryServiceImpl() {
        log.info("创建业务对象：CategoryServiceImpl");
    }

    @Override
    public void addNew(CategoryAddNewDTO categoryAddNewDTO) {
        log.debug("开始处理【添加类别】的业务，参数：{}", categoryAddNewDTO);

        // 查询父级类别
        Integer depth = 1;
        Long parentId = categoryAddNewDTO.getParentId();
        CategoryStandardVO parentCategory = null;
        if (parentId != 0) {
            // 确定当前类别的depth值，为：父级depth + 1
            parentCategory = categoryMapper.getStandardById(parentId);
            log.debug("根据父级类别ID【{}】查询父级类别详情，结果：{}", parentId, parentCategory);
            if (parentCategory == null) {
                String message = "添加类别失败，所选择的父级类别不存在！";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
            }
            depth = parentCategory.getDepth() + 1;
        }
        log.debug("当前尝试添加的类型的depth值为：{}", depth);

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
        // 补全Category对象的值：depth >>> 使用以上的depth变量
        category.setDepth(depth);
        // 补全Category对象的值：isParent >>> 0，新增的类别的isParent固定为0
        category.setIsParent(0);
        // 调用Mapper对象的方法，将数据插入到数据库，并获取返回值
        log.debug("准备向数据库中写入类别数据：{}", category);
        int rows = categoryMapper.insert(category);
        if (rows != 1) {
            String message = "添加类别失败，服务器忙，请稍后再尝试！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        // 检查当前新增类型的父级类别，如果父类别的isParent为0，则将父级类别的isParent更新为1
        if (parentId != 0) {
            if (parentCategory.getIsParent() == 0) {
                Category updateParentCategory = new Category();
                updateParentCategory.setId(parentId);
                updateParentCategory.setIsParent(1);
                log.debug("将父级类别的isParent更新为1，更新的参数对象：{}", updateParentCategory);
                rows = categoryMapper.update(updateParentCategory);
                if (rows != 1) {
                    String message = "添加类别失败，服务器忙，请稍后再尝试！";
                    log.debug(message);
                    throw new ServiceException(ServiceCode.ERR_UPDATE, message);
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除类别】的业务，参数：{}", id);
        // 调用Mapper对象的【根据id查询详情】查询数据，是当前尝试删除的数据
        CategoryStandardVO currentCategory = categoryMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (currentCategory == null) {
            // 是：数据不存在，抛出异常（ERR_NOT_FOUND）
            String message = "删除类别失败，尝试删除的类别不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查当前尝试删除的类别是否存在子级类别：判断以上查询结果的isParent是否为1
        if (currentCategory.getIsParent() == 1) {
            // 是：当前尝试删除的类别“是父级类别”（包含子级），抛出异常（ERR_CONFLICT）
            String message = "删除类别失败，尝试删除的类别仍包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 如果此类别关联了品牌，则不允许删除
        {
            int count = brandCategoryMapper.countByCategory(id);
            if (count > 0) {
                String message = "删除类别失败，当前类别仍关联了类别！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_DELETE, message);
            }
        }

        // 如果此类别关联了属性模板，则不允许删除
        {
            int count = categoryAttributeTemplateMapper.countByCategory(id);
            if (count > 0) {
                String message = "删除类别失败，当前类别仍关联了属性模板！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_DELETE, message);
            }
        }

        // 如果此类别关联了SPU，则不允许删除
        {
            int count = spuMapper.countByCategory(id);
            if (count > 0) {
                String message = "删除类别失败，当前类别仍关联了商品！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_DELETE, message);
            }
        }

        // 调用Mapper对象的【根据id删除】执行删除，并获取返回值
        int rows = categoryMapper.deleteById(id);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出异常（ERR_DELETE）
            String message = "删除类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }

        // ====== 如果这是父级类别中的最后一个子级，则将父级的isParent改为0 =====
        // 从当前尝试删除的类别对象中取出parentId
        Long parentId = currentCategory.getParentId();
        // 判断当前类别是否不为1级类别，即parentId不为0
        if (parentId != 0) {
            // 调用Mapper对象的countByParentId(parentId)进行统计
            int count = categoryMapper.countByParentId(parentId);
            // 判断统计结果是否为0
            if (count == 0) {
                // 创建新的Category对象，用于更新父级，此Category对象中需要封装：id（parentId），isParent（0）
                Category parentCategory = new Category();
                parentCategory.setId(parentId);
                parentCategory.setIsParent(0);
                // 调用Mapper对象的【更新】功能，执行修改数据，并获取返回值
                rows = categoryMapper.update(parentCategory);
                // 判断返回值是否不为1
                if (rows != 1) {
                    // 是：抛出异常（ERR_UPDATE）
                    String message = "删除类别失败，服务器忙，请稍后再尝试！";
                    log.warn(message);
                    throw new ServiceException(ServiceCode.ERR_UPDATE, message);
                }
            }
        }
    }

    @Override
    public void updateInfoById(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        log.debug("开始处理【修改类别详情】的业务，参数ID：{}, 新数据：{}", id, categoryUpdateDTO);
        // 检查名称是否被占用
        {
            int count = categoryMapper.countByNameAndNotId(id, categoryUpdateDTO.getName());
            if (count > 0) {
                String message = "修改类别详情失败，类别名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 调用Mapper对象的getDetailsById()方法执行查询
        CategoryStandardVO queryResult = categoryMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERR_NOT_FOUND)
            String message = "修改类别详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDTO, category);
        category.setId(id);

        // 修改数据
        log.debug("即将修改数据：{}", category);
        int rows = categoryMapper.update(category);
        if (rows != 1) {
            String message = "修改类别详情失败，服务器忙，请稍后再次尝试！";
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
    public void setDisplay(Long id) {
        updateDisplayById(id, 1);
    }

    @Override
    public void setHidden(Long id) {
        updateDisplayById(id, 0);
    }

    @Override
    public CategoryStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询类别详情】的业务，参数：{}", id);
        CategoryStandardVO category = categoryMapper.getStandardById(id);
        if (category == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERR_NOT_FOUND)
            String message = "查询类别详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return category;
    }

    @Override
    public List<CategoryListItemVO> list() {
        log.debug("开始处理【查询类别列表】的业务，无参数");
        return categoryMapper.list();
    }

    private void updateEnableById(Long id, Integer enable) {
        String[] tips = {"禁用", "启用"};
        log.debug("开始处理【{}类别】的业务，参数：{}", tips[enable], id);
        // 调用Mapper对象的getDetailsById()方法执行查询
        CategoryStandardVO queryResult = categoryMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            String message = tips[enable] + "类别失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的enable是否为1
        if (queryResult.getEnable().equals(enable)) {
            String message = tips[enable] + "类别失败，当前类别已经处于" + tips[enable] + "状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 准备执行更新
        Category category = new Category();
        category.setId(id);
        category.setEnable(enable);
        int rows = categoryMapper.update(category);
        if (rows != 1) {
            String message = tips[enable] + "类别失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    private void updateDisplayById(Long id, Integer isDisplay) {
        String[] tips = {"隐藏", "显示"};
        log.debug("开始处理【{}类别】的业务，参数：{}", tips[isDisplay], id);
        // 调用Mapper对象的getDetailsById()方法执行查询
        CategoryStandardVO queryResult = categoryMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            String message = tips[isDisplay] + "类别失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的enable是否为1
        if (queryResult.getIsDisplay().equals(isDisplay)) {
            String message = tips[isDisplay] + "类别失败，当前类别已经处于" + tips[isDisplay] + "状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 准备执行更新
        Category category = new Category();
        category.setId(id);
        category.setIsDisplay(isDisplay);
        int rows = categoryMapper.update(category);
        if (rows != 1) {
            String message = tips[isDisplay] + "类别失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

}
