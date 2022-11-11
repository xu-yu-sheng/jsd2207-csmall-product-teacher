package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.mapper.AlbumMapper;
import cn.tedu.csmall.product.mapper.PictureMapper;
import cn.tedu.csmall.product.pojo.dto.PictureAddNewDTO;
import cn.tedu.csmall.product.pojo.entity.Picture;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.service.IPictureService;
import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理图片业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class PictureServiceImpl implements IPictureService {

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private AlbumMapper albumMapper;

    public PictureServiceImpl() {
        log.info("创建业务对象：PictureServiceImpl");
    }

    @Override
    public void addNew(PictureAddNewDTO pictureAddNewDTO) {
        log.debug("开始处理【添加图片】的业务，参数：{}", pictureAddNewDTO);
        // 调用AlbumMapper对象的int countByName(String name)方法统计此名称的相册的数量
        Long albumId = pictureAddNewDTO.getAlbumId();
        AlbumStandardVO album = albumMapper.getStandardById(albumId);
        // 判断图片所属相册是否存在
        if (album == null) {
            // 是：相册名称已经存在，抛出RuntimeException异常
            String message = "添加图片失败！相册不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 准备需要添加到数据库的对象
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureAddNewDTO, picture);

        // 执行插入数据
        int rows = pictureMapper.insert(picture);
        if (rows != 1) {
            String message = "添加图片失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除图片】的业务，参数：{}", id);
        // 检查尝试删除的图片是否存在
        Object queryResult = pictureMapper.getStandardById(id);
        if (queryResult == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "删除图片失败，尝试访问的数据不存在！");
        }

        // 执行删除
        log.debug("即使删除id为{}的图片……", id);
        int rows = pictureMapper.deleteById(id);
        if (rows != 1) {
            throw new ServiceException(ServiceCode.ERR_DELETE, "删除图片失败，服务器忙，请稍后再次尝试！");
        }
        log.debug("删除完成！");
    }
}
