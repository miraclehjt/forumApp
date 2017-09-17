package com.chenzl.app.exception;

public class BusinessException extends Exception{
    
 /**
  * 
  */
 private static final long serialVersionUID = 1L;

 public BusinessException(String message){
     super(message);
 }
 
 public BusinessException(String message,Exception e){
     super(message,e);
 }
}
