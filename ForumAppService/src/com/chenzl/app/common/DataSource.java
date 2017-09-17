package com.chenzl.app.common;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 此注解用于在service层在方法上动态切换数据源
 * @author chenzl
 */
@Target({ElementType.METHOD, ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)
@Documented 
public @interface DataSource {
    
    String name() default DataSource.ftd;
    
    public static String  ftd = "forumDataSource";
 
  
}

