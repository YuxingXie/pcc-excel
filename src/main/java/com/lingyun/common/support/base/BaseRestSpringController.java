package com.lingyun.common.support.base;


import com.lingyun.common.support.data.ServerProject;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class BaseRestSpringController<T> {
    protected static final String CREATED_SUCCESS = "创建成功";
    protected static final String UPDATE_SUCCESS = "更新成功";
    protected static final String DELETE_SUCCESS = "删除成功";

    protected static final String RESULT_STRING = "result";
    protected static final String TOTAL = "total";
    protected static final String RESULT_ACTION = "redirect:/result";
//    private ApplicationContext applicationContext;
    private Map<String, ServerProject> projectMap;
//    private static Logger logger = LogManager.getLogger();

//    public BaseRestSpringController(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }



    public static void copyProperties(Object target, Object source) {
        BeanUtils.copyProperties(target, source);
    }

    protected void printRequestParameters(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        for (String key : requestMap.keySet()) {
            String str = new String();
            str += key + ":[";
            for (String val : requestMap.get(key)) {
                str += val + ",";
            }

        }
    }


    @SuppressWarnings("unchecked")
    protected T getOrCreateRequestAttribute(HttpServletRequest request, String key, Class<T> clazz) {
        Object value = request.getAttribute(key);
        if (value == null) {
            try {
                value = clazz.newInstance();
            } catch (Exception e) {
                ReflectionUtils.handleReflectionException(e);
            }
            request.setAttribute(key, value);
        }
        return (T) value;
    }

    protected String getHttpUrlString(HttpServletRequest request, String vieName) {
        String path = request.getContextPath();
        String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath + vieName;
    }

    protected String getInputHttpUrlString(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String uri = request.getRequestURI();
        return basePath + uri;
    }

    protected String getHttpUrlStringFromUri(HttpServletRequest request, String uri) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + uri;
    }
}
