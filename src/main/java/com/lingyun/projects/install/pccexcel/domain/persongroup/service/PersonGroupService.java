package com.lingyun.projects.install.pccexcel.domain.persongroup.service;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonGroupService {

    @Resource
    private PersonGroupRepository personGroupRepository;

}
