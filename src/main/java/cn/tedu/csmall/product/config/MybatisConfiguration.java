package cn.tedu.csmall.product.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.tedu.csmall.product.mapper")
@Configuration
public class MybatisConfiguration {
}
