package com.poscodx.mysite06.config;

import mysite.config.app.DBConfig;
import mysite.config.app.MyBatisConfig;
import mysite.config.app.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({DBConfig.class, MyBatisConfig.class, SecurityConfig.class})
@ComponentScan(basePackages={"mysite.service", "mysite.repository", "mysite.aspect"})
public class AppConfig {
}
