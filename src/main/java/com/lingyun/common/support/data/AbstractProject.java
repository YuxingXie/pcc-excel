package com.lingyun.common.support.data;

import com.lingyun.common.support.util.string.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


public abstract class AbstractProject implements Project{
    protected String name;//项目名称，英文，用在在项目uri映射及项目的文件路径上，所以命名要避免出现uri和文件系统错误
    public AbstractProject() {
        setAppRootDir();
    }



    public AbstractProject(String name) {
        this.name = name;
        setAppRootDir();
    }

    public String getName() {
        return name;
    }


    protected String appRootDir;

    private void setAppRootDir( ) {
        if(StringUtils.isNotBlank(this.appRootDir)) return ;
        StringBuilder uploadDir=new StringBuilder();
        String osName=System.getProperty("os.name");
        String appName;
        Properties properties = new Properties();
        //默认从gradle.properties中读取项目名称
        try {
            properties.load(new ClassPathResource("application.properties").getInputStream());
//            properties.load(new FileInputStream(new ClassPathResource("application.properties").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        appName=properties.getProperty("app.name");
        System.out.println("app name:"+appName);
        if (appName==null){
            appName = getProjectRunDirName();
        }
        if (appName==null) appName="lingyunApp_"+System.currentTimeMillis();
        if(osName.equals("Linux")||osName.equals("Mac OS X")||osName.equals("Unix")){
             this.appRootDir=uploadDir.append(System.getProperty("user.home"))
                    .append(File.separator)
                    .append("appData")
                    .append(File.separator)
                    .append(appName).toString();
        }else if(osName.toLowerCase().startsWith("win")){
            //windows下尽量放在D盘，没有则放在C盘
            File[] roots=File.listRoots();
            for (File root:roots){
                if (root.getAbsolutePath().equals("D:"+File.separator)){
                     this.appRootDir=uploadDir.append("D:").append(File.separator)
                            .append("appData")
                            .append(File.separator)
                            .append(appName).toString();
                    return;
                }
            }
             this.appRootDir=uploadDir.append("C:").append(File.separator)
                    .append("appData")
                    .append(File.separator)
                    .append(appName).toString();


        }else{
            //如果是未知的操作系统，放在运行目录下的appData下的项目名目录下
            this.appRootDir= uploadDir.append(System.getProperty("user.dir")).append(File.separator).append("appData").append(File.separator).append(appName).toString();
        }

    }

    /* 这是方法实质是获得运行目录的文件夹名称,在某些情况下这个文件夹名称可作为项目名称
     * @return
     */
    private static String getProjectRunDirName() {
        String projectName;
//        String[] dirs=System.getProperty("user.dir").split(File.separator);
//        projectName=dirs[dirs.length-1];
        String userDir=System.getProperty("user.dir");

        if(userDir==null) return null;

        if(!userDir.contains(File.separator)) return userDir;

        return userDir.substring(userDir.lastIndexOf(File.separator)+1);
    }
    public static void main(String[] args){
        System.out.println(getProjectRunDirName());
    }
}
