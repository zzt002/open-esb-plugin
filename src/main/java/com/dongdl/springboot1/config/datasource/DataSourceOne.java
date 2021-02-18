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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.dongdl.springboot1.dao.idao",
        sqlSessionFactoryRef = "data1SqlSessionFactory")
public class DataSourceOne {

    @Value("${mybatis.mapper-locations}")
	private String mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    /**
     * 返回data1数据库的数据源
     * @return
     */
    @Primary
    @Bean(name="data1Source")
    @ConfigurationProperties(prefix = "spring.datasource.data1")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 返回data1数据库的会话工厂
     * @param ds
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "data1SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("data1Source") DataSource ds) throws Exception{
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
     * 返回data1数据库的会话模板
     * @param sessionFactory
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "data1SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("data1SqlSessionFactory") SqlSessionFactory sessionFactory) throws  Exception{
        return  new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data1数据库的事务
     * @param ds
     * @return
     */
    @Primary
    @Bean(name = "data1TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("data1Source") DataSource ds){
        return new DataSourceTransactionManager(ds);
    }
}
