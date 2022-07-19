package br.com.gbcoding.tasksmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gbcoding.tasksmanager.models.Task;
import br.com.gbcoding.tasksmanager.repositories.TaskRepository;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;
  
  public List<Task> listAll(String keyword){
    if(keyword != null){
      return taskRepository.findAll(keyword);
    }
    return taskRepository.findAll();
  }
}
