package com.project.ppmtool.services;

import com.project.ppmtool.domain.Backlog;
import com.project.ppmtool.domain.Project;
import com.project.ppmtool.domain.User;
import com.project.ppmtool.exception.ProjectIdException;
import com.project.ppmtool.exception.ProjectNotFoundException;
import com.project.ppmtool.repositories.BacklogRepository;
import com.project.ppmtool.repositories.ProjectRepositoriy;
import com.project.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepositoriy projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;


    @Autowired
    private UserRepository userRepository;


    public Project saveOrUpdate(Project project,String username)
    {
        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }
        try{
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());


            String  id = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(id);

            if(project.getId()== null)
            {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(id);
            }

            if(project.getId()!=null)
            {
                project.setBacklog(backlogRepository.findByProjectIdentifier(id));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findById(String id,String username)
    {
        //Only want to return the project if the user looking for it is the owner

        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+ id +"' does not exist");

        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;


    }

    public Iterable<Project> findAll(String username)
    {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectById(String identifier,String username){


        projectRepository.delete(findById(identifier,username));

    }

    public int updateProject(String identifier)
    {
return 0;
    }

}
