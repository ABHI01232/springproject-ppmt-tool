package com.project.ppmtool.repositories;

import com.project.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepositoriy extends CrudRepository<Project,Long> {

    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);

    @Override
    Iterable<Project> findAll();



    Project findByProjectIdentifier(String projectId);
}

