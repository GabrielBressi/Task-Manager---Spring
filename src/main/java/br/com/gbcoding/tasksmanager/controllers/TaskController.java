package br.com.gbcoding.tasksmanager.controllers;
import br.com.gbcoding.tasksmanager.TaskService;
import br.com.gbcoding.tasksmanager.dto.RequisitionFormTask;
import br.com.gbcoding.tasksmanager.models.StatusTask;
import br.com.gbcoding.tasksmanager.repositories.TaskRepository;
import java.util.Optional;
import br.com.gbcoding.tasksmanager.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService service;


    @GetMapping("/tasks")
    public ModelAndView index(){
        List<Task> tasks = this.taskRepository.findAll();
        ModelAndView mv = new ModelAndView("tasks/index");
        mv.addObject("tasks", tasks);
        return mv;
    }

    // New Task
    @GetMapping("/tasks/new")
    public ModelAndView newTask(RequisitionFormTask requisitionFormTask){
        ModelAndView mv = new ModelAndView("tasks/new");
        mv.addObject("statusTask", StatusTask.values());
        return mv;
    }

    @PostMapping("/tasks")
    public ModelAndView create(@Valid RequisitionFormTask requisitionFormTask, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("tasks/new");
            mv.addObject("statusTask", StatusTask.values());
            return mv;
        } else {
            Task task = requisitionFormTask.toTask();
            this.taskRepository.save(task);
            ModelAndView mv = new ModelAndView("redirect:/tasks");
            mv.addObject("message", "Task created successfully!");
            mv.addObject("error", false);
            return mv;
        }
    }

    // Show task Details
    @GetMapping("tasks/{id}")
    public ModelAndView show(@PathVariable Long id){
        Optional<Task> optional = this.taskRepository.findById(id);

        if(optional.isPresent()){
            Task task = optional.get();
            ModelAndView mv = new ModelAndView("tasks/show");
            mv.addObject("task", task);
            return mv;
        }
        else {
            ModelAndView mv = this.returnErrorTask("SHOW ERROR: Something went wrong");
            return mv;
        }
    }

    // Edit Task
    @GetMapping("tasks/{id}/edit") // Showing the edit form
    public ModelAndView edit(@PathVariable Long id, RequisitionFormTask requisition){
        Optional<Task> optional = this.taskRepository.findById(id);

        if(optional.isPresent()){
            Task task = optional.get();
            requisition.fromTask(task);
            ModelAndView mv = new ModelAndView("tasks/edit");
            mv.addObject("taskId", task.getId());
            mv.addObject("statusTask", StatusTask.values());

            return mv;
        }
        else {
            ModelAndView mv = this.returnErrorTask("EDIT ERROR: Something went wrong!");
            return mv;
        }
    }

    @PostMapping("tasks/{id}") // Updating the Task's values
    public ModelAndView update(@PathVariable Long id, @Valid RequisitionFormTask requisition, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("tasks/edit");
            mv.addObject("taskId", id);
            mv.addObject("statusTask", StatusTask.values());
            return mv;
        } else {
            Optional<Task> optional = this.taskRepository.findById(id);
            if(optional.isPresent()){
                Task task = requisition.toTask(optional.get());
                this.taskRepository.save(task);
                ModelAndView mv = new ModelAndView("redirect:/tasks");
                mv.addObject("message", "Task updated successfully");
                mv.addObject("error", false);
                return mv;
            } else {
                ModelAndView mv = this.returnErrorTask("UPDATE ERROR: Something went wrong!");
                return mv;
            }
        }
    }

    // Delete 

    @GetMapping("tasks/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/tasks");
        try{
            this.taskRepository.deleteById(id);
            mv.addObject("message", "Task deleted successfully");
            mv.addObject("error", false);
        }
        catch(EmptyResultDataAccessException e){
            System.out.println(e);
            mv = this.returnErrorTask("DELETE ERROR: Something went wrong!");
            return mv;
        }

        return mv;
    }

    // Search 
    @GetMapping("tasks/search")
    public ModelAndView search(@Param("keyword") String keyword){
        List<Task> tasks = this.service.listAll(keyword);
        ModelAndView mv = new ModelAndView("tasks/search");
        
        mv.addObject("tasks", tasks);
        return mv;
    }


    // message error 
    private ModelAndView returnErrorTask(String msg){
        ModelAndView mv = new ModelAndView("redirect:/tasks");
        mv.addObject("message", msg);
        mv.addObject("error", true);
        return mv;
    }


}
