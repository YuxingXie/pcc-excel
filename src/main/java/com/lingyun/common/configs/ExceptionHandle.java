package com.lingyun.common.configs;

import com.lingyun.common.support.code.MessageLevel;
import com.lingyun.common.support.data.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseData<?> HandleException(Exception e){
        e.printStackTrace();
        return new ResponseData<>(false,"服务器异常："+e.getMessage(), MessageLevel.WRONG.getCode(),null);
    }

}
