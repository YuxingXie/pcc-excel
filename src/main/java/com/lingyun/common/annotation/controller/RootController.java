package com.lingyun.common.annotation.controller;

import com.lingyun.common.support.data.ServerProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
@Controller
public class RootController {
    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    private ApplicationContext applicationContext;
    private ServerProject mainProject;
    public RootController(ApplicationContext applicationContext) {
        this.applicationContext=applicationContext;

    }
/**
 * TODO 因为这里只考虑了前后端分离的项目，而没有考虑服务端渲染的项目，所以需要再完善，这个先注释掉
 */
//    @GetMapping("/")
//    public String index(){
//        if(mainProject==null){
//            //为什么是否存在主项目的判断逻辑要放在控制器的方法中而不是放在容器的启动过程中呢？因为调用这个控制器方法时，可以肯定所有的spring bean都已经初始化了，而在创建容器过程中则未必
//            Map<String, ServerProject> projectMap=applicationContext.getBeansOfType(ServerProject.class);
//            for(ServerProject project:projectMap.values()){
//                if(project.isMain()){
//                    mainProject=project;
//                }
//            }
//            assert mainProject!=null;//一定有一个主项目，否则容器无法启动，逻辑见Application.onApplicationEvent(...)方法
//        }
//
//        return "forward:/"+mainProject.getHomeProject().getName()+"/public/"+mainProject.getHomeProject().getHomePage();
//
//    }
}
