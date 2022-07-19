package br.com.gbcoding.tasksmanager.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.gbcoding.tasksmanager.models.StatusTask;
import br.com.gbcoding.tasksmanager.models.Task;

public class RequisitionFormTask {

  @NotBlank
  @NotNull
  private String taskName;

  private StatusTask statusTask;

  public String getTaskName() {
    return taskName;
  }
  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }
  public StatusTask getStatusTask() {
    return statusTask;
  }
  public void setStatusTask(StatusTask statusTask) {
    this.statusTask = statusTask;
  }
  public Task toTask() {
    Task task = new Task();
    task.setTaskName(this.taskName);
    task.setStatusTask(this.statusTask);
    return task;
  }

  public void fromTask(Task task){
    this.taskName = task.getTaskName();
    this.statusTask = task.getStatusTask();
  }

  public Task toTask(Task task){
    task.setTaskName(this.taskName);
    task.setStatusTask(this.statusTask);
    return task;
  }

}
