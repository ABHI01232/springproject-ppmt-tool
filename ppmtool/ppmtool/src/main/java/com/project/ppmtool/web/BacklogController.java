package com.project.ppmtool.web;

import com.project.ppmtool.domain.ProjectTask;
import com.project.ppmtool.services.ProjectTaskService;
import com.project.ppmtool.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null)return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask,principal.getName());
                return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getAllBacklogsofProject( @PathVariable String backlog_id,Principal principal)
    {
        return projectTaskService.findBacklogById(backlog_id,principal.getName());
    }
    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable  String backlog_id,@PathVariable String pt_id,Principal principal)
    {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
        return new ResponseEntity<>(projectTask,HttpStatus.OK);
        }
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,@PathVariable  String backlog_id,@PathVariable String pt_id,Principal principal)
    {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null)return errorMap;
        ProjectTask updateTask = projectTaskService.updateByProjectSequence(projectTask,backlog_id,pt_id,principal.getName());
        return new ResponseEntity<>(updateTask,HttpStatus.OK);
    }
    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id,principal.getName());

        return new ResponseEntity<String>("Project Task "+pt_id+" was deleted successfully", HttpStatus.OK);
    }




}
