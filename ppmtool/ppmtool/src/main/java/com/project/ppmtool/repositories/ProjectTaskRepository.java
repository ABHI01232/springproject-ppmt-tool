package com.project.ppmtool.repositories;

import com.project.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {
    List<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);
    ProjectTask findByProjectSequence(String sequence);

}

