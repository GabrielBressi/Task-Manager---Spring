package br.com.gbcoding.tasksmanager.repositories;

import br.com.gbcoding.tasksmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query("SELECT t FROM Task t WHERE t.taskName LIKE %?1%")
  public List<Task> findAll(String keyword);

}
