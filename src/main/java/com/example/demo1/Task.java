package com.example.demo1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data // Automatically generates all Getters, Setters, toString(), and equals() methods!
@NoArgsConstructor // Automatically generates a blank constructor: public Task() {}
@AllArgsConstructor // Automatically generates a constructor with all fields
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
}