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


}
