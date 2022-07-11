package com.project.ppmtool.services;

import com.project.ppmtool.domain.Project;
import com.project.ppmtool.exception.ProjectIdException;
import com.project.ppmtool.repositories.ProjectRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepositoriy projectRepository;

    public Project saveOrUpdate(Project project)
    {
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findById(String id)
    {
        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID"+ id +"does not exist");

        }

        return project;


    }

    public Iterable<Project> findAll()
    {
        return projectRepository.findAll();
    }

    public void deleteProjectById(String identifier){

        Project projectToDelete = projectRepository.findByProjectIdentifier(identifier.toUpperCase());
        if(projectToDelete==null)
        {
            throw new ProjectIdException("Project ID "+ identifier +" does not exist");
        }
        projectRepository.delete(projectToDelete);

    }

    public int updateProject(String identifier)
    {
return 0;
    }

}
