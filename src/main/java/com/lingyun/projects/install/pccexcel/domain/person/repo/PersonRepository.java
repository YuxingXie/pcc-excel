package com.lingyun.projects.install.pccexcel.domain.person.repo;

import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}
