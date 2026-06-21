package com.example.demo1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository needs two types: <The Entity Class, The Primary Key Type>
public interface TaskRepository extends JpaRepository<Task, Long> {
    // By extending JpaRepository, you instantly get:
    // save(), findById(), findAll(), deleteById(), and more for free!
}