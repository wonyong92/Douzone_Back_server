package com.example.bootproject.config;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
@MapperScan("com.example.bootproject.repository.mapper")
public class MybatisConfig {
    public SqlSessionFactory getSessionFactory(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);

        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
