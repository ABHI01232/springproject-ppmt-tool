package com.project.ppmtool.services;

import com.project.ppmtool.domain.Backlog;
import com.project.ppmtool.domain.Project;
import com.project.ppmtool.domain.ProjectTask;
import com.project.ppmtool.exception.ProjectNotFoundException;
import com.project.ppmtool.repositories.BacklogRepository;
import com.project.ppmtool.repositories.ProjectRepositoriy;
import com.project.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepositoriy projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
        try {
            //solving exception project not found
            //project task(pt) needs tobe added to a specific project , and project cannot be null and Backlog(bl) should exist
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the bl to pt
            projectTask.setBacklog(backlog);
            // we want our project sequence to be like this IDPRO-1....100
            Integer BacklogSequence = backlog.getPTSequence();
            //UPDATE THE BL sequence
            BacklogSequence++;
            //Add sequence to the project task
            backlog.setPTSequence(BacklogSequence);

            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //INITIAL priority when priority null
            if (projectTask.getPriority() == null) //In the future we need projectTask.getPriority()== 0 to handle the form
            {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);

        } catch (Exception e) {
            throw new ProjectNotFoundException("Project Not Found");

        }

    }
    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);
        if(project==null)
        {
            throw new ProjectNotFoundException("Project with ID : ' "+id+"' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id , String pt_id)
    {
        return projectTaskRepository.findByProjectSequence(pt_id);
    }
}

