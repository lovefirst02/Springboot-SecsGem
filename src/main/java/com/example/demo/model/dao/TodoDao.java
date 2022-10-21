package com.example.demo.model.dao;

import com.example.demo.model.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo,Integer> {
}
