package com.lingyun.common.support.data;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 服务端项目
 */
public class ServerProject extends AbstractProject{

    private static final Logger logger = LoggerFactory.getLogger(ServerProject.class);
    /**
     * 是否主项目，举例：有2个服务器项目life和smswall，当设置life项目为main时，
     * 则访问应用程序root url http://localhost:8082时，会重定向到http://localhost:8082/life
     */
    private boolean main;
    /**
     * 工作目录是指前端代码的目录，一般是angular等编译后的代码,有些项目有多个前端项目，比如手机端和管理端.这个Map的key是这些子项目的名称
     * 比如life项目有两个前端子项目life-m和lifecms，那么它会有两个工作目录entry,key是life-m和lifecms
     */
    private Set<FrontProject> frontProjects=new HashSet<>();


    /**
     * 主前端项目，它与其它前端项目比，会占用应用程序的根url，比如项目life包含2个子项目life-m和life-cms,
     * 当life-m被设置为项目的homeProject,则访问应用程序url http://localhost:8082/life,
     * 会访问life-m的首页
     */
    private FrontProject homeProject;
    public ServerProject(String name) {
        Set<FrontProject> frontProjects=new HashSet<>();
        FrontProject frontProject=new FrontProject(name,name);
        frontProjects.add(frontProject);
        this.name=name;
        this.frontProjects=frontProjects;
        this.homeProject=frontProject;
        init();
    }
    public ServerProject(String name, Set<FrontProject> frontProjects) {
        this.name=name;
        this.frontProjects=frontProjects;
        init();

    }
    public ServerProject(String name, Set<FrontProject> frontProjects,FrontProject homeProject ) {
        this.name=name;
        assert frontProjects.contains(homeProject);
        this.frontProjects=frontProjects;
        this.homeProject=homeProject;
        init();

    }
    public ServerProject(String name, Set<FrontProject> frontProjects,FrontProject homeProject,boolean main) {
        this.name=name;
        assert frontProjects.contains(homeProject);
        this.frontProjects=frontProjects;
        this.homeProject=homeProject;
        this.main=main;
        init();

    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Set<FrontProject> getFrontProjects() {
        return frontProjects;
    }


    public void init(){
        assert StringUtils.isNotBlank(name);
//        makeUploadDirAndWorkingDir();
        makeHomeProject();
    }



    public FrontProject getHomeProject() {
        return homeProject;
    }

    private void makeHomeProject() {
        if (this.homeProject!=null) return;
        if(frontProjects.size()==1){
            this.homeProject=frontProjects.iterator().next();
            return;
        }
        for(FrontProject frontProject:frontProjects){
            if(frontProject.name.equals(name)){
                this.homeProject=frontProject;
                return;
            }
        }
        this.homeProject=frontProjects.iterator().next();
    }

    public FrontProject getFrontProjectByName(String frontProjectName){
        if (BeanUtil.emptyCollection(frontProjects)) return null;
        for(FrontProject frontProject:frontProjects){
            if (frontProject.getName().equals(name)){
                return frontProject;
            }
        }
        return null;
    }

    //生成项目文件上传目录
    private void makeUploadDirAndWorkingDir() {
        //项目的上传路径必须是一个文件系统目录，它与workingDir无关，无论workingDir是类路径还是文件路径，uploadDir也要创建，它依赖于操作系统和项目信息
        if (BeanUtil.emptyCollection(frontProjects)) return;
        for(FrontProject frontProject:frontProjects){
            String subProjectWorkingDir=frontProject.getWorkingDir();
            if(frontProject.getDefaultWorkingDir().equals(subProjectWorkingDir)){
                logger.info("为项目"+frontProject.getName()+"创建工作目录:"+subProjectWorkingDir);
                File projectUploadDirFile=new File(subProjectWorkingDir);
                if(!projectUploadDirFile.exists()||!projectUploadDirFile.isDirectory()){
                    projectUploadDirFile.mkdirs();
                }else{
                    logger.info("项目"+frontProject.getName()+"工作目录已存在："+subProjectWorkingDir+"，忽略创建!");
                }
            }else {
                logger.info("项目"+frontProject.getName()+"工作目录与默认目录不一致，此目录可能为开发目录，将不会创建："+subProjectWorkingDir);
            }
            String subProjectUploadDir=frontProject.getUploadDir();
            logger.info("为项目"+frontProject.getName()+"创建上传目录:"+subProjectUploadDir);
            File subProjectUploadDirFile=new File(subProjectUploadDir);
            if(!subProjectUploadDirFile.exists()||!subProjectUploadDirFile.isDirectory()){
                subProjectUploadDirFile.mkdirs();
            }
        }

    }
}
