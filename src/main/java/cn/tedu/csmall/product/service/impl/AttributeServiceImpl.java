package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AttributeMapper;
import cn.tedu.csmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.csmall.product.pojo.dto.AttributeAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AttributeUpdateInfoDTO;
import cn.tedu.csmall.product.pojo.entity.Attribute;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.tedu.csmall.product.service.IAttributeService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理属性业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    public AttributeServiceImpl() {
        log.info("创建业务对象：AttributeServiceImpl");
    }

    @Override
    public void addNew(AttributeAddNewDTO attributeAddNewDTO) {
        log.debug("开始处理【添加属性】的业务，参数：{}", attributeAddNewDTO);
        // 调用Mapper对象的int countByName(String name)方法统计此名称的属性的数量
        String name = attributeAddNewDTO.getName();
        Long templateId = attributeAddNewDTO.getTemplateId();
        int countByName = attributeMapper.countByNameAndTemplate(name, templateId);
        log.debug("尝试在属性模板【{}】中添加属性【{}】，在数据库中此名称的属性数量为：{}", templateId, name, countByName);
        // 判断统计结果是否大于0
        if (countByName > 0) {
            // 是：属性名称已经存在，抛出RuntimeException异常
            String message = "添加属性失败！此属性模板中已经存在名称为【" + name + "】的属性！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 检查属性模板是否存在
        {
            Object queryResult = attributeTemplateMapper.getStandardById(templateId);
            if (queryResult == null) {
                String message = "添加属性失败！属性模板不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
            }
        }

        // 创建对象
        Attribute attribute = new Attribute();
        // 复制属性
        BeanUtils.copyProperties(attributeAddNewDTO, attribute);
        // 执行插入数据
        int rows = attributeMapper.insert(attribute);
        if (rows != 1) {
            String message = "添加属性失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void updateInfoById(Long id, AttributeUpdateInfoDTO attributeUpdateInfoDTO) {
        log.debug("开始处理【修改属性详情】的业务，参数ID：{}, 新数据：{}", id, attributeUpdateInfoDTO);
        // 调用adminMapper根据参数id执行查询
        AttributeStandardVO queryResult = attributeMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 抛出ServiceException，业务状态码：40400
            String message = "修改属性详情失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查名称是否被占用
        {
            Long templateId = queryResult.getTemplateId();
            int count = attributeMapper.countByNameAndTemplateAndNotId(id, attributeUpdateInfoDTO.getName(), templateId);
            if (count > 0) {
                String message = "修改属性详情失败，属性名称已经被占用！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
            }
        }

        // 创建Admin对象，将作为修改时的参数
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeUpdateInfoDTO, attribute);
        attribute.setId(id);

        // 调用Mapper对象的update()修改属性基本资料，并获取返回值
        log.debug("即将修改属性详情：{}", attribute);
        int rows = attributeMapper.update(attribute);
        // 判断返回值是否不等于1
        if (rows != 1) {
            // 是：抛出ServiceException（ERR_INSERT）
            String message = "修改属性详情失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    @Override
    public AttributeStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询属性详情】的业务，参数：{}", id);
        AttributeStandardVO attribute = attributeMapper.getStandardById(id);
        if (attribute == null) {
            // 是：此id对应的数据不存在，则抛出异常(ERR_NOT_FOUND)
            String message = "查询属性详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return attribute;
    }

    @Override
    public List<AttributeListItemVO> listByTemplateId(Long templateId) {
        log.debug("开始处理【根据属性模板查询属性列表】的业务，参数：{}", templateId);
        return attributeMapper.listByTemplateId(templateId);
    }

}
