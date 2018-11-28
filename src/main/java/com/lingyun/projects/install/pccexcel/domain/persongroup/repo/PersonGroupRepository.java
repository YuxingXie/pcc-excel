package com.lingyun.projects.install.pccexcel.domain.persongroup.repo;

import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonGroupRepository extends JpaRepository<PersonGroup, String> {


}
