package com.dongdl.springboot1.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.dongdl.springboot1.dao.pdao", sqlSessionFactoryRef = "data3SqlSessionFactory")
public class DataSourceThree {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;


    /**
     * 返回data3数据库的数据源
     * @return
     */
    @Bean(name="data3Source")
    @ConfigurationProperties(prefix = "spring.datasource.data3")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 返回data3数据库的会话工厂
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean(name = "data3SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("data3Source") DataSource ds) throws Exception{
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        bean.setTypeAliasesPackage(typeAliasesPackage);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    /**
     * 返回data3数据库的会话模板
     * @param sessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "data3SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("data3SqlSessionFactory") SqlSessionFactory sessionFactory) throws  Exception{
        return  new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data3数据库的事务
     * @param ds
     * @return
     */
    @Bean(name = "data3TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("data3Source") DataSource ds){
        return new DataSourceTransactionManager(ds);
    }
}
