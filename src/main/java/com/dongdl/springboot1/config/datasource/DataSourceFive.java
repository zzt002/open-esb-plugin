package com.dongdl.springboot1.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.dongdl.springboot1.dao.vpn2testdao",
        sqlSessionFactoryRef = "data5SqlSessionFactory")
public class DataSourceFive {

    /**
     * 返回data5数据库的数据源
     * @return
     */
    @Bean(name="data5Source")
    @ConfigurationProperties(prefix = "spring.datasource.data5")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 返回data5数据库的会话工厂
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean(name = "data5SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("data5Source") DataSource ds) throws Exception{
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(ds);
        return bean.getObject();
    }

    /**
     * 返回data5数据库的会话模板
     * @param sessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "data5SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("data5SqlSessionFactory") SqlSessionFactory
                                                             sessionFactory) throws  Exception{
        return  new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data5数据库的事务
     * @param ds
     * @return
     */
    @Bean(name = "data5TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("data5Source") DataSource ds){
        return new DataSourceTransactionManager(ds);
    }

}
