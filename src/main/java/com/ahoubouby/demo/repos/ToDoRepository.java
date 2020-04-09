package com.ahoubouby.demo.repos;
/*
 * abdelwahed created on 06/04/2020
 * E-MAI: ahoubouby@gmail.com
 */

import com.ahoubouby.demo.dmain.Todo;
import org.springframework.data.repository.CrudRepository;


public interface ToDoRepository extends CrudRepository<Todo,String> {
   
}
