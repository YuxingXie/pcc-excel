package com.lingyun.common.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
//    @Profile("prod")
//    @Bean
//    public DataSource dataSourceOracle() {
//        DriverManagerDataSource ds=new DriverManagerDataSource();
//        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        ds.setImageUri("jdbc:oracle:thin:@10.96.51.10:1521:orcl");
//        ds.setUsername("lingyun");
//        ds.setPassword("lingyun");
//        return ds;
//    }
    @Profile({"xyximac","xyxmac","xyxwin","site,smzjwin"})
    @Bean
    public DataSource dataSourceH2_02() {
        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:~/pcc;AUTO_SERVER=TRUE");
        ds.setUsername("sa");
        return ds;
    }

    @Profile("test")
    @Bean
    public DataSource dataSourceTest() {
        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://192.168.1.118:3306/testuu?useUnicode=true&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceMysql() {
        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://10.96.51.81:3306/zhnx?useUnicode=true&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }
}
