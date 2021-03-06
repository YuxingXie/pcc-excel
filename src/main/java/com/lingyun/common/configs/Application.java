package com.lingyun.common.configs;


import com.lingyun.projects.install.pccexcel.components.JTextComponentPrintStream;
import com.lingyun.projects.install.pccexcel.components.frames.HomeFrame;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
        (scanBasePackages = {
        "com.lingyun.projects.install.pccexcel.domain.**.service",
        "com.lingyun.projects.install.**.config",
        "com.lingyun.common.configs"
        })
@EnableJpaRepositories(basePackages = {"com.lingyun.projects.install.**.repo"})
@EntityScan(basePackages ={"com.lingyun.projects.install.**.entity"})
public class Application implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {


        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).headless(false).run(args);

    }




    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context=contextRefreshedEvent.getApplicationContext();
        ExcelRepository excelRepository=context.getBean(ExcelRepository.class);
        PersonGroupRepository personGroupRepository=context.getBean(PersonGroupRepository.class);
        Constant.currentExcel=excelRepository.findByLastOpenDateGreatest();
        System.out.println("当前excel:"+(Constant.currentExcel==null?"无":Constant.currentExcel.getPath()));
        long count=personGroupRepository.count();
        if(count== 0){
            PersonGroup personGroup1=new PersonGroup();
            PersonGroup personGroup2=new PersonGroup();
            PersonGroup personGroup3=new PersonGroup();
            PersonGroup personGroup4=new PersonGroup();
            personGroup1.setGroupName("主席");
            personGroup2.setGroupName("委员");
            personGroup3.setGroupName("乡镇");
            personGroup4.setGroupName("政协机关");
            List<PersonGroup> personGroups=new ArrayList<>();
            personGroups.add(personGroup1);
            personGroups.add(personGroup2);
            personGroups.add(personGroup3);
            personGroups.add(personGroup4);
            personGroupRepository.save(personGroups);
        }
        JTextField consoleTextField=context.getBean("consoleTextField",JTextField.class);
//        JTextComponentPrintStream mps = new JTextComponentPrintStream(System.out,consoleTextField );
//        System.setOut(mps);
//        System.setErr(mps);
        HomeFrame appFrame = context.getBean(HomeFrame.class);
//        appFrame.pack();
        appFrame.setVisible(true);
    }

}