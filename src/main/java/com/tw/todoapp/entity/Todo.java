package com.tw.todoapp.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private  Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private boolean priority;

    public Todo(Long id,String description,boolean completed) {
        this.id=id;
        this.description=description;
        this.completed=completed;
    }
    public Todo(String description , boolean completed) {
        this.description=description;
        this.completed=completed;
    }
    public Todo(String description , boolean completed,boolean priority) {
        this.description=description;
        this.completed=completed;
        this.priority=priority;
    }


}
