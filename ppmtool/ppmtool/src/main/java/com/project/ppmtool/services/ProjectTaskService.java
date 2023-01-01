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


    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask,String userName) {

            //solving exception project not found
            //project task(pt) needs tobe added to a specific project , and project cannot be null and Backlog(bl) should exist
            Backlog backlog = projectService.findById(projectIdentifier,userName).getBacklog();
            //set the bl to pt
            projectTask.setBacklog(backlog);
            // we want our project sequence to be like this IDPRO-1....100
            Integer BacklogSequence = backlog.getPTSequence();
            //UPDATE THE BL sequence
            BacklogSequence++;
            //Add sequence to the project task
            backlog.setPTSequence(BacklogSequence);

            //Add sequence to Project Task
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //INITIAL priority when priority null
            if (projectTask.getPriority()==null||projectTask.getPriority()==0) //In the future we need projectTask.getPriority()== 0 to handle the form
            {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);



    }
    public Iterable<ProjectTask> findBacklogById(String id,String userName) {
       projectService.findById(id,userName);
       return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id , String pt_id,String userName)
    {
        //make sure we are searching on an existing backlog
      projectService.findById(backlog_id,userName);

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }

        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updateTask,String backlog_id,String pt_id,String userName)
    {
        ProjectTask  projectTask = findPTByProjectSequence(backlog_id,pt_id,userName);
        projectTask = updateTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id,String pt_id,String userName)
    {
        ProjectTask  projectTask = findPTByProjectSequence(backlog_id,pt_id,userName);
        projectTaskRepository.delete(projectTask);
    }


}

