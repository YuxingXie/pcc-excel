package com.lingyun.projects.install.pccexcel.config;

import com.lingyun.projects.install.pccexcel.components.HomeFrame;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.route.Observable;
import com.lingyun.projects.install.pccexcel.route.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JComponentsConfig {
    @Bean
    public HomeFrame homeFrame(JPanelRouter observer,ExcelService excelService, PersonGroupRepository personGroupRepository,
                               ExcelDataRepository excelDataRepository, PersonRepository personRepository) {
        return new HomeFrame(observer,excelService,personGroupRepository,excelDataRepository,personRepository);
    }
    @Bean
    public JPanelRouter observer(){
        Publisher publisher=new Publisher();
        Observable source$=new Observable(publisher);
        JPanelRouter observer= new JPanelRouter();

        source$.onSubscribe(observer);
        return observer;
    }
}
