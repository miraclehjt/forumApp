package com.chenzl.app.common;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import com.chenzl.app.common.DataSource;
import com.chenzl.app.common.DataSourceContextHolder;

@Component
@Aspect
public class DataSourceExchange  implements MethodBeforeAdvice, AfterReturningAdvice{
	 public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
	            throws Throwable {
	        
	        DataSourceContextHolder.clearDataSourceType();
	        
	    }

	    public void before(Method method, Object[] args, Object target) throws Throwable {
	        
	        if (method.isAnnotationPresent(DataSource.class)) {
	            
	            DataSource datasource = method.getAnnotation(DataSource.class);
	            DataSourceContextHolder.setDataSourceType(datasource.name());
	            
	        } else {
	            
	            DataSourceContextHolder.setDataSourceType(DataSource.ftd);
	        }

	    }
}
