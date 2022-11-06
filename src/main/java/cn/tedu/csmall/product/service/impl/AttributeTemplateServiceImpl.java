package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AttributeMapper;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.mapper.CategoryAttributeTemplateMapper;
import cn.tedu.csmall.product.mapper.SpuMapper;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.service.IAttributeTemplateService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理属性模板业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {

    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;
    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;
    @Autowired
    private SpuMapper spuMapper;

    public AttributeTemplateServiceImpl() {
        log.info("创建业务对象：AttributeTemplateServiceImpl");
    }

    @Override
    public void addNew(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO) {
        log.debug("开始处理【添加属性模板】的业务，参数：{}", attributeTemplateAddNewDTO);
        // 调用Mapper对象的int countByName(String name)方法统计此名称的属性的数量
        String name = attributeTemplateAddNewDTO.getName();
        int countByName = attributeTemplateMapper.countByName(name);
        log.debug("尝试添加的属性模板名称是：{}，在数据库中此名称的属性模板数量为：{}", name, countByName);
        // 判断统计结果是否大于0
        if (countByName > 0) {
            // 是：属性模板名称已经存在，抛出RuntimeException异常
            String message = "添加属性模板失败！属性模板名称【" + name + "】已存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建对象
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        // 复制属性
        BeanUtils.copyProperties(attributeTemplateAddNewDTO, attributeTemplate);
        // 执行插入数据
        log.debug("准备向数据库中写入属性模板数据：{}", attributeTemplate);
        int rows = attributeTemplateMapper.insert(attributeTemplate);
        if (rows != 1) {
            String message = "添加属性模板失败，服务器忙，请稍后再尝试！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除属性模板】的业务，参数：{}", id);
        // 检查尝试删除的属性是否存在
        Object queryResult = attributeTemplateMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "删除属性模板失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        // 如果有属性关联到了此属性模板，不允许删除
        {
            int count = attributeMapper.countByTemplateId(id);
            if (count > 0) {
                String message = "删除属性模板失败！当前属性模板仍存在关联的属性！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 如果有类别关联到了此属性模板，不允许删除
        {
            int count = categoryAttributeTemplateMapper.countByAttributeTemplate(id);
            if (count > 0) {
                String message = "删除属性模板失败！当前属性模板仍存在关联的类别！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 如果有SPU关联到了此属性模板，不允许删除
        {
            int count = spuMapper.countByAttributeTemplate(id);
            if (count > 0) {
                String message = "删除属性模板失败！当前属性模板仍存在关联的SPU！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 执行删除
        log.debug("即使删除id为{}的属性……", id);
        int rows = attributeTemplateMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除属性模板失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("删除完成！");
    }

}
