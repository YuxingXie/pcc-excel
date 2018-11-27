package com.lingyun.common.support.data;

import com.lingyun.common.support.util.string.StringUtils;

import java.io.File;

/**
 * 前端项目
 */
public class FrontProject extends AbstractProject{

    private String workingDir;
    private String defaultWorkingDir;//默认的工作目录，如果不指定工作目录则使用此目录，此目录基于操作系统和项目名称
    private String uploadDir;
    private String uploadUri;
    private String serverProjectName;
    private String homePage="index.html";//项目的首页
    public FrontProject(String name,String serverProjectName) {

        assert StringUtils.isNotBlank(name);
        assert StringUtils.isNotBlank(serverProjectName);
        this.name=name;
        this.serverProjectName=serverProjectName;

        init();
    }

    public String getHomePage() {
        return homePage;
    }

    public FrontProject(String name, String serverProjectName, String workingDir) {
        assert StringUtils.isNotBlank(name);
        assert StringUtils.isNotBlank(serverProjectName);
        assert StringUtils.isNotBlank(workingDir);
        this.workingDir=workingDir;
        this.name = name;
        this.serverProjectName=serverProjectName;

        init();
    }
    public FrontProject(String name,String serverProjectName, String workingDir,String homePage) {
        assert StringUtils.isNotBlank(name);
        assert StringUtils.isNotBlank(serverProjectName);
        assert StringUtils.isNotBlank(workingDir);
        this.workingDir=workingDir;
        this.name = name;
        this.serverProjectName=serverProjectName;
        if(StringUtils.isNotBlank(homePage)){
            this.homePage=homePage;
        }
        init();
    }

    public String getDefaultWorkingDir() {
        return defaultWorkingDir;
    }

    public String getWorkingDir() {
        return workingDir;
    }


    public String getUploadDir() {
        return uploadDir;
    }

    public String getUploadUri() {
        return uploadUri;
    }

    public void init(){



        if(this.name.equals(serverProjectName)){
            this.defaultWorkingDir = appRootDir +File.separator+serverProjectName;
            if(this.workingDir==null){
                this.workingDir = this.defaultWorkingDir;
            }
            this.uploadUri=serverProjectName+"/upload";
            this.uploadDir= appRootDir + File.separator+serverProjectName+File.separator+"upload";

        }else{
            this.defaultWorkingDir= appRootDir +File.separator+serverProjectName+File.separator+name;
            if(this.workingDir==null){
                this.workingDir = this.defaultWorkingDir;
            }
            this.uploadUri=serverProjectName+"/"+this.name+"/upload";
            this.uploadDir=this.workingDir+File.separator+"upload";

        }



    }
}
