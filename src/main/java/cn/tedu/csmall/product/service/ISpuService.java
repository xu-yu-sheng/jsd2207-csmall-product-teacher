package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.SpuAddNewDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spu业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface ISpuService {

    /**
     * 添加Spu
     *
     * @param spuAddNewDTO 添加的Spu对象
     */
    void addNew(SpuAddNewDTO spuAddNewDTO);

}
