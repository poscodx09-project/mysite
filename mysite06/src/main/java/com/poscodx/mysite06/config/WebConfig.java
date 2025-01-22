package com.poscodx.mysite06.config;


import com.poscodx.mysite06.config.web.FileUploadConfig;
import com.poscodx.mysite06.config.web.LocaleConfig;
import com.poscodx.mysite06.config.web.MvcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
//@Import({MvcConfig.class, LocaleConfig.class, FileUploadConfig.class})
//@ComponentScan({"com.poscodx.mysite06.controller", "com.poscodx.mysite06.exception"})
public class WebConfig {
}
