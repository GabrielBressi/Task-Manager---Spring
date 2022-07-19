package br.com.gbcoding.tasksmanager.models;    
import javax.persistence.*;

@Entity // Usando o Hibernate , declarando uma entidade, ou seja um model para o Banco de Dados
public class Task{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String taskName;
        @Enumerated(EnumType.STRING)
        private StatusTask statusTask;

        public Task(){}
        public Task(String taskName, StatusTask statusTask) {
                this.taskName = taskName;
                this.statusTask = statusTask;
        }



        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

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
}
