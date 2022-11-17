package cn.tedu.csmall.product.config;

import cn.tedu.csmall.product.mybatis.InsertUpdateTimeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Mybatis配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@MapperScan("cn.tedu.csmall.product.mapper")
public class MybatisConfiguration {

    public MybatisConfiguration() {
        log.debug("创建配置类对象：MybatisConfiguration");
    }

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct // 此注解添加在方法上，表示此方法是Spring Bean的生命周期方法中的初始化方法，会在创建对象、自动装配之后，自动执行
    public void addInterceptor() {
        log.debug("开始注册Mybatis拦截器");
        InsertUpdateTimeInterceptor interceptor = new InsertUpdateTimeInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }

}
