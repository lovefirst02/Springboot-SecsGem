package com.example.demo.controller;

import com.example.demo.base.Result;
import com.example.demo.model.entity.Todo;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todo")
    public Result getTodoList(){
        Result result = todoService.getTodos();
        return result;
    }

    @PostMapping(value = "/create")
    public Result createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @PutMapping(value = "/update")
    public Result updateTodo(@RequestBody Todo todo){
        return todoService.updateTodo(todo.getId());
    }

    @DeleteMapping(value = "/delete")
    public Result deleteTodo(@RequestBody Todo todo){
        return todoService.deleteTodo(todo.getId());
    }
}
