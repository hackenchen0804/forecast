package com.hacken.forecast.config;

import com.hacken.forecast.util.Base64Convert;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.UnsupportedEncodingException;

@Configuration
public class Config {
    @Autowired
    Base64Convert base64Convert;

    @Value("${spring.datasource.driver-class-name}")
    String jdbcDriver;

    @Value("${spring.datasource.url}")
    String jdbcUrl;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;


    @Bean
    public DataSource dataSource() throws PropertyVetoException, UnsupportedEncodingException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(base64Convert.md5decode(password));
        return dataSource;
    }

}
