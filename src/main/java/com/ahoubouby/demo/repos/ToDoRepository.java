package com.ahoubouby.demo.repos;
/*
 * abdelwahed created on 06/04/2020
 * E-MAI: ahoubouby@gmail.com
 */

import com.ahoubouby.demo.dmain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ToDoRepository extends MongoRepository<Todo, String> {
    
}
