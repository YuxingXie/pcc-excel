package com.lingyun.common.annotation;

import com.lingyun.common.support.data.FrontProject;
import com.lingyun.common.support.data.ServerProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.Set;

public abstract class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
    private static boolean hasInstance;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //hasInstance可以保证这个方法只执行一次
        if (hasInstance) return;
        registry.addMapping("/**").allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
        hasInstance=true;
    }


    protected void registerProjectResource(ResourceHandlerRegistry registry, ServerProject serverProject) {
        //注册项目静态资源及路径映射
        Set<FrontProject> frontProjects=serverProject.getFrontProjects();
        for(FrontProject frontProject:frontProjects){
            String frontProjectName = frontProject.getName();
            String frontProjectWorkingDir = frontProject.getWorkingDir();
            registry.addResourceHandler("/"+ frontProjectName +"/public/**").addResourceLocations("file:"+frontProjectWorkingDir +File.separator);
            logger.warn(frontProjectName+":工作目录 "+"/"+ frontProjectName +"/public/**"+" 映射到 "+"file:"+frontProjectWorkingDir +File.separator);

            String frontProjectUploadDir = frontProject.getUploadDir();
            registry.addResourceHandler("/"+ frontProjectName +"/upload/**").addResourceLocations("file:"+frontProjectUploadDir+File.separator);
            logger.warn(frontProjectName+":上传目录 "+"/"+frontProjectName+"/upload/**"+" 映射到 "+"file:"+frontProjectUploadDir+File.separator);
        }
    }

}