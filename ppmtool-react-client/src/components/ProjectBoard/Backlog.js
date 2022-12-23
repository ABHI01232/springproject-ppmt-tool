import React, { Component } from 'react';
import ProjectTask from "./ProjectTasks/ProjectTask";

 class Backlog extends Component {
  render() {
    const {project_tasks_prop}= this.props;
    const tasks = project_tasks_prop.map(project_task =>(
      <ProjectTask key ={project_task.id} project_task={project_task}/>
    ))
    let todoItems=[];
    let inprogressItems =[];
    let doneItems =[];

   for(let i =0;i<tasks.length; i++)
   {
    if(tasks[i].props.project_task.status === "TO_DO")
    {
      todoItems.push(tasks[i]);
    }

    if(tasks[i].props.project_task.status === "IN_PROGRESS")
    {
      inprogressItems.push(tasks[i]);
    }


    if(tasks[i].props.project_task.status === "DONE")
    {
      doneItems.push(tasks[i]);
    }
   }

    return (
          <div className="container">
            <div className="row">
              <div className="col-md-4">
                <div className="card text-center mb-2">
                  <div className="card-header bg-secondary text-white">
                    <h3>TO DO</h3>
                  </div>
                </div>
                {todoItems}
                {
                  // <!-- SAMPLE PROJECT TASK STARTS HERE -->
                }
                {/* <div className="card mb-1 bg-light">
                  <div className="card-header text-primary">
                    ID: projectSequence -- Priority: priorityString
                  </div>
                  <div className="card-body bg-light">
                    <h5 className="card-title">project_task.summary</h5>
                    <p className="card-text text-truncate ">
                      project_task.acceptanceCriteria
                    </p>
                    <a href="" className="btn btn-primary">
                      View / Update
                    </a>
  
                    <button className="btn btn-danger ml-4">Delete</button>
                  </div>
                </div>
   */}
                {
                  // <!-- SAMPLE PROJECT TASK ENDS HERE -->
                }
              </div>
              <div className="col-md-4">
                <div className="card text-center mb-2">
                  <div className="card-header bg-primary text-white">
                    <h3>In Progress</h3>
                  </div>
                </div>
                {inprogressItems}
                {
                  //  <!-- SAMPLE PROJECT TASK STARTS HERE -->
                  //         <!-- SAMPLE PROJECT TASK ENDS HERE -->
                }
              </div>
              <div className="col-md-4">
                <div className="card text-center mb-2">
                  <div className="card-header bg-success text-white">
                    <h3>Done</h3>
                  </div>
                </div>
                {doneItems}
                {
                  // <!-- SAMPLE PROJECT TASK STARTS HERE -->
                  // <!-- SAMPLE PROJECT TASK ENDS HERE -->
                }
              </div>
            </div>
          </div>
    );
  }
}
export default Backlog;