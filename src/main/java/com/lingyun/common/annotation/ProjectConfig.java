package com.lingyun.common.annotation;

import com.lingyun.common.support.data.FrontProject;
import com.lingyun.common.support.data.ServerProject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;


public abstract class ProjectConfig  {
    @Value("${spring.profiles.active}")
    protected String activeProfile;

    protected ServerProject registerProject(String projectName){


        return new ServerProject(projectName);
    }

    protected ServerProject registerProject(String projectName,  String workingDir){

        FrontProject frontProject=new FrontProject(projectName,projectName,workingDir);

        return registerProject(projectName,frontProject);
    }

    protected ServerProject registerProject(String projectName, FrontProject frontProject){
        Set<FrontProject> frontProjects=new HashSet<>();
        frontProjects.add(frontProject);
       return registerProject(projectName,frontProjects);
    }
    protected ServerProject registerProject(String projectName, Set<FrontProject> frontProjects){
        ServerProject project;
//        assert StringUtils.isNotBlank(activeProfile);
        //在非开发环境，即便指定workingDir也不会生效，这个目录会由框架自动指定到相对于Project.APP_ROOT_DIR下
//        if(!activeProfile.equals("prod")){
//            project= new ServerProject(projectName,frontProjects);
//        }else {
//            project= new ServerProject(projectName);
//        }
        project= new ServerProject(projectName,frontProjects);
        return project;
    }
}