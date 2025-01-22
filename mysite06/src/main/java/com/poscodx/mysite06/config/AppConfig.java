package com.poscodx.mysite06.config;


import com.poscodx.mysite06.config.app.DBConfig;
import com.poscodx.mysite06.config.app.MyBatisConfig;
import com.poscodx.mysite06.config.app.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
//@Import({DBConfig.class, MyBatisConfig.class, SecurityConfig.class})
//@ComponentScan(basePackages={"com.poscodx.mysite06.service", "com.poscodx.mysite06.repository", "com.poscodx.mysite06.aspect"})
public class AppConfig {

}
