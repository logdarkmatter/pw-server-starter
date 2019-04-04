package com.ulht.pw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ulht.pw.PWApplicationConstants;

@Configuration
@ComponentScan(basePackages = { PWApplicationConstants.ANNOTATION_CONFIG_PACKAGE })
public class BeanConfig {

}
