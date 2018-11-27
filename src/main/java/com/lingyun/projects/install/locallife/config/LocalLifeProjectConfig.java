package com.lingyun.projects.install.locallife.config;

import com.lingyun.common.annotation.ProjectConfig;
import com.lingyun.common.support.data.FrontProject;
import com.lingyun.common.support.data.ServerProject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashSet;
import java.util.Set;

@Configuration
public class LocalLifeProjectConfig extends ProjectConfig {


    @Bean
    public ServerProject projectLocalLife(){
        String serverProjectName="local-life";
        Set<FrontProject> frontProjectSet=new HashSet<>();
        frontProjectSet.add(new FrontProject("local-life-cms",serverProjectName));
        frontProjectSet.add(new FrontProject("local-life-m",serverProjectName));
//        switch (activeProfile){
//            case "xyximac":{
//                //公司的imac
//                frontProjectSet.add(new FrontProject("local-life-cms",serverProjectName));
//                frontProjectSet.add(new FrontProject("local-life-m",serverProjectName));
//            } break;
//            case "xyxmac":{
//                //谢宇星的macbook
//                frontProjectSet.add(new FrontProject("life-cms",serverProjectName,"/Users/xieyuxing/WebstormProjects/lifehomecms/dist"));
//                frontProjectSet.add(new FrontProject("life-m",serverProjectName,"/Users/xieyuxing/WebstormProjects/life-m/www"));
//            } break;
//            case "xyxwin":{
//                //谢宇星家里的windows7
//                frontProjectSet.add(new FrontProject("life-cms",serverProjectName,"D:\\develop\\Projects\\WebstormProjects\\lifehomecms\\dist"));
//                frontProjectSet.add(new FrontProject("life-m",serverProjectName,"D:\\develop\\Projects\\WebstormProjects\\life-m\\www"));
//            } break;
//            default:break;
//        }

        return registerProject(serverProjectName,frontProjectSet);
    }
}
