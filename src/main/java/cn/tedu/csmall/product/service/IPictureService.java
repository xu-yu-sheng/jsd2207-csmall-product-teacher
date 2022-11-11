package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.PictureAddNewDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图片业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IPictureService {

    /**
     * 添加图片
     *
     * @param pictureAddNewDTO 图片
     */
    void addNew(PictureAddNewDTO pictureAddNewDTO);

    /**
     * 删除图片
     *
     * @param id 图片id
     */
    void delete(Long id);

}
