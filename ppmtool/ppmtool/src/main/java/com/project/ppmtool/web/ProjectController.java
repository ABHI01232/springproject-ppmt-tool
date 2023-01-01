package com.project.ppmtool.web;

import com.project.ppmtool.domain.Project;
import com.project.ppmtool.services.ProjectService;
import com.project.ppmtool.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result ,Principal principal)
    {
        ResponseEntity<?> errorsResponse = validationErrorService.MapValidationService(result);
        if(errorsResponse!=null) return errorsResponse;

      Project project1=  projectService.saveOrUpdate(project,principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable String id,Principal principal)
    {
        Project fetchedProject = projectService.findById(id,principal.getName());
        return new ResponseEntity<Project>(fetchedProject,HttpStatus.OK);
    }


    @GetMapping("/all")
    public Iterable<Project> findAll(Principal principal)
    {
        return projectService.findAll(principal.getName());
    }



    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId,Principal principal){
        projectService.deleteProjectById(projectId,principal.getName());

        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
    }


}
